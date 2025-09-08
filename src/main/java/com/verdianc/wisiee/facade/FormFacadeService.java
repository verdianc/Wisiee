package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.DTO.*;
import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Exception.BaseException;
import com.verdianc.wisiee.Exception.Form.FormFileLimitExceededException;
import com.verdianc.wisiee.Service.Interface.FileService;
import com.verdianc.wisiee.Service.Interface.FormService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormFacadeService {

  private final FormService formService;
  private final FileService fileService;

  public FormDTO createForm(FormRequestDTO request) {
    return formService.createForm(request);
  }

  public FormDTO getForm(Long id) {
    return formService.getForm(id);
  }

  public List<FormDTO> getFormList() {
    return formService.getFormList();
  }

  public FormDTO updateForm(Long id, FormRequestDTO request) {
    return formService.updateForm(id, request);
  }

  public void deleteForm(Long id) {
    formService.deleteForm(id);
  }


  //================== 파일 관련 =====================//

  // 파일 첨부
  public FileDTO addFileToForm(Long formId, FileRequestDTO request,
      byte[] fileData, String contentType) {
    List<FileDTO> existingFiles = fileService.getFileList(formId);
    if (existingFiles.size() >= 3) {
      throw new FormFileLimitExceededException();
    }
    return fileService.createFile(formId, request, fileData, contentType);
  }


  // 파일 목록 조회
  public List<FileDTO> getFilesOfForm(Long formId) {
    return fileService.getFileList(formId);
  }

  // 파일 단건 조회
  public FileDTO getFile(Long fileId) {
    return fileService.getFile(fileId);
  }

  // 파일 삭제
  public void deleteFile(Long fileId) {
    fileService.deleteFile(fileId);
  }


}
