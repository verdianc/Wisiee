package com.verdiance.wisiee.DTO.File;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileDTO {

  private Long id;

  private String bucket;

  private String objectKey;

  private String versionId;

  private Long size;

  private String name;

  private String description;

  private Long formId;

}
