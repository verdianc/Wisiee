package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Entity.FormEntity;
import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.Form.*;
import com.verdianc.wisiee.Mapper.FormMapper;
import com.verdianc.wisiee.Repository.FormJpaRepository;
import com.verdianc.wisiee.Service.Interface.FormService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

  private final FormJpaRepository formJpaRepository;
  private final FormMapper formMapper;

  @Override
  @Transactional
  public FormDTO createForm(FormRequestDTO request, UserEntity user) {
    try {
      // code 관련 검증
      if (!request.isPublic() && (request.getCode() == null || request.getCode().isBlank())) {
        throw new CodeRequiredException();
      }
      if (!request.isPublic() &&
          (request.getCode().length() < 5 || request.getCode().length() > 20)) {
        throw new InvalidCodeLengthException(request.getCode().length());
      }

      FormEntity entity = formMapper.toEntity(request, user);
      FormEntity saved = formJpaRepository.save(entity);
      return formMapper.toDTO(saved);

    } catch (Exception e) {
      throw new FormCreateFailedException(e.getMessage());
    }
  }

  @Override
  public FormDTO updateForm(Long id, FormRequestDTO request) {
    FormEntity entity = formJpaRepository.findById(id)
        .orElseThrow(() -> new FormNotFoundException(id));

    // 1. 비공개 전환이면 code 필수 + 길이 제한
    if (!request.isPublic()) {
      validateCode(request.getCode());
    }

    try {
      entity.update(request);
      return formMapper.toDTO(entity);
    } catch (Exception e) {
      throw new FormUpdateFailedException(e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public FormDTO getForm(Long id) {
    FormEntity entity = formJpaRepository.findById(id)
        .orElseThrow(() -> new FormNotFoundException(id));
    return formMapper.toDTO(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FormDTO> getFormList() {
    return formJpaRepository.findAll()
        .stream()
        .map(formMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteForm(Long id) {
    FormEntity entity = formJpaRepository.findById(id)
        .orElseThrow(() -> new FormNotFoundException(id));

    try {
      formJpaRepository.delete(entity);
    } catch (Exception e) {
      throw new FormDeleteFailedException(id);
    }
  }

  // ================== private helper ================== //

  private void validateCode(String code) {
    if (code == null || code.isBlank()) {
      throw new CodeRequiredException();
    }
    if (code.length() < 5 || code.length() > 20) {
      throw new InvalidCodeLengthException(code.length());
    }
  }

}
