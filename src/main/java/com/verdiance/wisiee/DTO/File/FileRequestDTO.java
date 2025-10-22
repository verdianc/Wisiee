package com.verdiance.wisiee.DTO.File;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileRequestDTO {

  private String name;

  private String description;

}
