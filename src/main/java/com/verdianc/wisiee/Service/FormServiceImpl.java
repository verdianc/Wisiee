package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Entity.FormEntity;
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
  public FormDTO createForm(FormRequestDTO request) {
    try {
      FormEntity entity = formMapper.toEntity(request);
      FormEntity saved = formJpaRepository.save(entity);
      return formMapper.toDTO(saved);
    } catch (Exception e) {
      throw new FormCreateFailedException(e.getMessage());
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
  public FormDTO updateForm(Long id, FormRequestDTO request) {
    FormEntity entity = formJpaRepository.findById(id)
        .orElseThrow(() -> new FormNotFoundException(id));

    try {
      entity.update(request);
      return formMapper.toDTO(entity);
    } catch (Exception e) {
      throw new FormUpdateFailedException(e.getMessage());
    }
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

}
