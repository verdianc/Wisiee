package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.Entity.FormEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Form.CodeRequiredException;
import com.verdiance.wisiee.Exception.Form.FormCreateFailedException;
import com.verdiance.wisiee.Exception.Form.FormDeleteFailedException;
import com.verdiance.wisiee.Exception.Form.FormNotFoundException;
import com.verdiance.wisiee.Exception.Form.FormUpdateFailedException;
import com.verdiance.wisiee.Exception.Form.InvalidCodeLengthException;
import com.verdiance.wisiee.Mapper.FormMapper;
import com.verdiance.wisiee.Repository.FormJpaRepository;
import com.verdiance.wisiee.Service.Interface.FormService;
import com.verdiance.wisiee.Service.Interface.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormServiceImpl implements FormService {

    private final FormJpaRepository formJpaRepository;
    private final FormMapper formMapper;
    private final ProductService productService;


    @Override
    @Transactional
    public FormDTO createForm(FormRequestDTO request, UserEntity user) {
        try {
            String code = Optional.ofNullable(request.getCode())
                    .map(String::trim)
                    .orElse(null);

            if (!request.isPublic() && (code==null || code.isEmpty())) {
                log.error("Code is required for a private form.");
                throw new CodeRequiredException();
            }


            if (!request.isPublic() && (code.length() < 5 || code.length() > 20)) {
                log.error("Invalid code length: {}", code.length());
                throw new InvalidCodeLengthException(code.length());
            }

            FormEntity entity = formMapper.toEntity(request, user);

            FormEntity saved = formJpaRepository.save(entity);

            return formMapper.toDTO(saved);

        } catch (Exception e) {
            log.error("An error occurred during form creation: {}", e.getMessage(), e);
            throw new FormCreateFailedException(e.getMessage());
        }
    }

    @Override
    public FormDTO updateForm(Long id, FormRequestDTO request, UserEntity user) {
        FormEntity entity = formJpaRepository.findById(id)
                .orElseThrow(() -> new FormNotFoundException(id));

        if (!entity.getUser().getUserId().equals(user.getUserId())) {
            throw new FormUpdateFailedException("작성자가 아닌 사용자는 수정할 수 없습니다.");
        }

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
    public FormDTO getForm(Long id, String code) {
        FormEntity entity = formJpaRepository.findById(id)
                .orElseThrow(() -> new FormNotFoundException(id));

        log.info("Received code: '{}' (length: {})", code, (code!=null ? code.length():"null"));
        log.info("DB code:       '{}' (length: {})", entity.getCode(), (entity.getCode()!=null ? entity.getCode().length():"null"));
        log.info("Is it equal? (case-sensitive): {}", entity.getCode().equals(code));
        log.info("Are they equal? (case-insensitive): {}", entity.getCode().equalsIgnoreCase(code));
        log.info("--- End of Comparison ---");

        if (!entity.isPublic()) {
            if (code==null || !entity.getCode().equals(code)) {
                throw new CodeRequiredException();
            }
        }

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
    public void deleteForm(Long id, UserEntity user) {
        FormEntity entity = formJpaRepository.findById(id)
                .orElseThrow(() -> new FormNotFoundException(id));

        // 생성자 검증
        if (!entity.getUser().getUserId().equals(user.getUserId())) {
            throw new FormDeleteFailedException(id);
        }

        try {
            formJpaRepository.delete(entity);
        } catch (Exception e) {
            throw new FormDeleteFailedException(id);
        }
    }


    private void validateCode(String code) {
        if (code==null || code.isBlank()) {
            throw new CodeRequiredException();
        }
        if (code.length() < 5 || code.length() > 20) {
            throw new InvalidCodeLengthException(code.length());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<FormDTO> getFormsByUser(UserEntity user) {

        return formJpaRepository.findByUser(user).stream()
            .map(formMapper::toDTO)
            .toList();
    }
}
