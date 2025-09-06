package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.Entity.FileEntity;
import com.verdianc.wisiee.Entity.FormEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

  public FileEntity toEntity(FileRequestDTO dto,
      FormEntity form,
      String bucket,
      String objectKey,
      String versionId,
      Long size) {

    return FileEntity.builder()
        .bucket(bucket)
        .objectKey(objectKey)
        .versionId(versionId)
        .size(size)
        .name(dto.getName())
        .description(dto.getDescription())
        .form(form)
        .build();
  }

  public FileDTO toDTO(FileEntity entity) {
    return FileDTO.builder()
        .id(entity.getId())
        .bucket(entity.getBucket())
        .objectKey(entity.getObjectKey())
        .versionId(entity.getVersionId())
        .size(entity.getSize())
        .name(entity.getName())
        .description(entity.getDescription())
        .formId(entity.getForm().getId())
        .build();
  }

}
