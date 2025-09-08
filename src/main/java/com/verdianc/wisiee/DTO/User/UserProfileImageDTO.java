package com.verdianc.wisiee.DTO.User;

import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserProfileImageDTO {

  private Long userId;
  private FileRequestDTO fileRequest;
  private byte[] fileData;
  private String contentType;

}
