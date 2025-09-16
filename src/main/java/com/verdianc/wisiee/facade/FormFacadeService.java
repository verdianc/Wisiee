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

  public FormDTO createForm(FormRequestDTO request, List<MultipartFile> files) {
    // 1. 현재 로그인 유저 조회
    UserEntity user = userService.getUser();

    // 2. Form 생성
    FormDTO formDTO = formService.createForm(request, user);
    Long formId = formDTO.getId();

    // 3. 파일 첨부
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




  // 비공개 폼 단건 조회
  public FormDTO getForm(Long id, String code) {
    return formService.getForm(id, code);
  }


  public FormDTO updateForm(Long id, FormRequestDTO request, List<MultipartFile> files) {
    UserEntity user = userService.getUser(); // 세션 사용자

    FormDTO formDTO = formService.updateForm(id, request, user);

    // 파일 처리
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

    return formDTO; // 최종 조회 결과 반환
  }




  public void deleteForm(Long formId) {
    // 현재 로그인한 사용자
    UserEntity user = userService.getUser();

    // 서비스단에서 작성자 검증 + 삭제
    formService.deleteForm(formId, user);
  }




}
