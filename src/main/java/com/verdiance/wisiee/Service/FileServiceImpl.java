package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.File.FileDTO;
import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import com.verdiance.wisiee.Entity.FileEntity;
import com.verdiance.wisiee.Entity.FormEntity;
import com.verdiance.wisiee.Exception.File.FileDeleteFailedException;
import com.verdiance.wisiee.Exception.File.FileNotFoundException;
import com.verdiance.wisiee.Exception.File.FileUploadFailedException;
import com.verdiance.wisiee.Infrastructure.S3.S3Port;
import com.verdiance.wisiee.Mapper.FileMapper;
import com.verdiance.wisiee.Repository.FileJpaRepository;
import com.verdiance.wisiee.Repository.FormJpaRepository;
import com.verdiance.wisiee.Service.Interface.FileService;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final FileJpaRepository fileJpaRepository;
  private final FormJpaRepository formJpaRepository;
  private final FileMapper fileMapper;
  private final S3Port s3Port;

  @Override
  @Transactional
  public FileDTO createFile(Long formId, FileRequestDTO request, byte[] fileData, String contentType) {
    FormEntity form = formJpaRepository.findById(formId)
        .orElseThrow(() -> new FileNotFoundException(formId));

    String objectKey = UUID.randomUUID().toString();

    try {
      S3Port.PutResult put = s3Port.put(objectKey, fileData, contentType, Map.of());

      FileEntity entity = fileMapper.toEntity(
          request, form, s3Port.bucket(), objectKey, put.versionId(), put.size()
      );


      FileEntity saved = fileJpaRepository.save(entity);

      if (form.getFiles() != null) {
        form.getFiles().add(saved);
      }

      return fileMapper.toDTO(saved);
    } catch (Exception e) {
      throw new FileUploadFailedException("파일 업로드 실패: " + e.getMessage());
    }
  }




  @Override
  @Transactional(readOnly = true)
  public FileDTO getFile(Long id) {
    FileEntity file = fileJpaRepository.findById(id)
        .orElseThrow(() -> new FileNotFoundException(id));
    return fileMapper.toDTO(file);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FileDTO> getFileList(Long formId) {
    return fileJpaRepository.findByFormId(formId).stream()
        .map(fileMapper::toDTO)
        .toList();
  }

  @Override
  @Transactional
  public void deleteFile(Long id) {
    FileEntity file = fileJpaRepository.findById(id)
        .orElseThrow(() -> new FileNotFoundException(id));

    try {
      s3Port.delete(file.getObjectKey(), file.getVersionId());
      fileJpaRepository.delete(file);
    } catch (Exception e) {
      throw new FileDeleteFailedException(id, e.getMessage());
    }
  }




  @Override
  @Transactional
  public void removeAllFilesByFormId(Long formId) {
    FormEntity form = formJpaRepository.findById(formId)
        .orElseThrow(() -> new FileNotFoundException(formId));
    if (form.getFiles() != null) {
      form.getFiles().clear();
    }
  }
}
