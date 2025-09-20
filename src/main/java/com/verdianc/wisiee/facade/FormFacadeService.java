package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.DTO.*;
import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.BaseException;
import com.verdianc.wisiee.Exception.File.FileUploadFailedException;
import com.verdianc.wisiee.Exception.Form.FormFileLimitExceededException;
import com.verdianc.wisiee.Exception.User.SessionUserNotFoundException;
import com.verdianc.wisiee.Service.Interface.FileService;
import com.verdianc.wisiee.Service.Interface.FormService;
import com.verdianc.wisiee.Service.Interface.ProductService;
import com.verdianc.wisiee.Service.Interface.UserService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    if (request.getProducts() != null && !request.getProducts().isEmpty()) {
      request.getProducts().forEach(p -> {
        p.setFormId(formId);
        productService.createProduct(p);
      });
      formDTO.setProducts(productService.getProductsByFormId(formId));
    }

    // 4. 파일 첨부
    if (files != null && !files.isEmpty()) {
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

  public FormDTO updateForm(Long id, FormRequestDTO request, List<MultipartFile> files) {
    UserEntity user = userService.getUser();

    // 1. Form 수정
    FormDTO formDTO = formService.updateForm(id, request, user);

    // 2. Product 수정 (폼 단위에서 통째로 교체)
    if (request.getProducts() != null) {
      // 기존 product 전부 삭제
      productService.deleteProductsByFormId(id);

      // 새 product 저장
      request.getProducts().forEach(p -> {
        p.setFormId(id);
        productService.createProduct(p);
      });

      // 최신 product 붙여주기
      formDTO.setProducts(productService.getProductsByFormId(id));
    }

    // 3. 파일 처리
    if (files != null && !files.isEmpty()) {
      if (files.size() > 3) {
        throw new FormFileLimitExceededException();
      }
      for (MultipartFile file : files) {
        try {
          FileRequestDTO meta = FileRequestDTO.builder()
              .name(file.getOriginalFilename())
              .description("업데이트된 첨부파일")
              .build();

          fileService.createFile(
              formDTO.getId(),
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


  public void deleteForm(Long formId) {
    UserEntity user = userService.getUser();
    formService.deleteForm(formId, user);
  }

}
