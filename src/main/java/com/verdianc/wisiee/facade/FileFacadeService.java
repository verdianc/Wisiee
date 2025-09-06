package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.DTO.*;
import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.Exception.BaseException;
import com.verdianc.wisiee.Service.Interface.FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileFacadeService {


  private final FileService fileService;

  public FileDTO createFile(Long formId, FileRequestDTO request, byte[] fileData, String contentType) {
    return fileService.createFile(formId, request, fileData, contentType);
  }

  public FileDTO getFile(Long id) {
    return fileService.getFile(id);
  }

  public List<FileDTO> getFileList(Long formId) {
    return fileService.getFileList(formId);
  }

  public void deleteFile(Long id) {
    fileService.deleteFile(id);
  }

}
