package com.verdiance.wisiee.Facade;

import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.File.FileUploadFailedException;
import com.verdiance.wisiee.Exception.Form.FormFileLimitExceededException;
import com.verdiance.wisiee.Service.Interface.FileService;
import com.verdiance.wisiee.Service.Interface.FormService;
import com.verdiance.wisiee.Service.Interface.ProductService;
import com.verdiance.wisiee.Service.Interface.UserService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormFacadeService {

    private final FormService formService;
    private final FileService fileService;
    private final UserService userService;
    private final ProductService productService;


    public FormDTO createForm(FormRequestDTO request, List<MultipartFile> files) {
        // 1. 현재 로그인 유저 조회
        UserEntity user = userService.getUser();

        // 2. Form 생성
        FormDTO formDTO = formService.createForm(request, user);
        Long formId = formDTO.getId();

        // 3. Product 초기값 저장
        if (request.getProducts()!=null && !request.getProducts().isEmpty()) {
            request.getProducts().forEach(p -> {
                p.setFormId(formId);
                productService.createProduct(p);
            });
            formDTO.setProducts(productService.getProductsByFormId(formId));
        }

        // 4. 파일 첨부
        if (files!=null && !files.isEmpty()) {
            if (files.size() > 3) {
                throw new FormFileLimitExceededException();
            }
            for (MultipartFile file : files) {
                try {
                    FileRequestDTO meta = FileRequestDTO.builder()
                            .name(file.getOriginalFilename())
                            .description("첨부파일")
                            .build();

                    fileService.createFile(
                            formId,
                            meta,
                            file.getBytes(),
                            file.getContentType()
                    );
                } catch (Exception e) {
                    throw new FileUploadFailedException(e.getMessage());
                }
            }
        }

        return formDTO;
    }

    public FormDTO getForm(Long id, String code) {
        FormDTO formDTO = formService.getForm(id, code);
        formDTO.setProducts(productService.getProductsByFormId(id));
        return formDTO;
    }

    @Transactional
    public FormDTO updateForm(Long id, FormRequestDTO request, List<MultipartFile> files) {
        UserEntity user = userService.getUser();

        // 1. 기본 정보 수정
        formService.updateForm(id, request, user);

        // 2. Product 수정
        if (request.getProducts() != null) {
            productService.updateProducts(id, request.getProducts());
        }

        // 3. 파일 처리 (추가만 하는 로직)
        if (files != null && !files.isEmpty()) {
            if (files.size() > 3) throw new FormFileLimitExceededException();
            for (MultipartFile file : files) {
                try {
                    FileRequestDTO meta = FileRequestDTO.builder()
                        .name(file.getOriginalFilename())
                        .description("업데이트된 첨부파일")
                        .build();
                    fileService.createFile(id, meta, file.getBytes(), file.getContentType());
                } catch (Exception e) {
                    throw new FileUploadFailedException(e.getMessage());
                }
            }
        }

        // ★ 중요: 수정이 모두 끝난 후, '새로고침'된 데이터를 조회해서 반환
        // 그래야 영속성 컨텍스트에 꼬여있던 리스트가 아니라 DB 상태 그대로의 DTO를 만듭니다.
        return getForm(id, request.getCode());
    }

    public void deleteForm(Long formId) {
        UserEntity user = userService.getUser();
        formService.deleteForm(formId, user);
    }

    public List<FormDTO> getMyForms() {
        UserEntity user = userService.getUser();
        return formService.getFormsByUser(user);
    }


    public Page<FormDTO> getAllForms(Pageable pageable) {
        return formService.getFormList(pageable);
    }

}
