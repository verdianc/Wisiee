package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.File.FileDTO;
import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import java.util.List;

public interface FileService {

  FileDTO createFile(Long formId, FileRequestDTO request, byte[] fileData, String contentType);

  FileDTO getFile(Long id);

  List<FileDTO> getFileList(Long formId);

  void deleteFile(Long id);

}
