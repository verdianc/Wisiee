package com.verdiance.wisiee.DTO.User;

import java.io.IOException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserProfileImageDTO {

  private Long userId;
  private byte[] fileData;
  private String contentType;

  public static UserProfileImageDTO fromMultipart(Long userId, MultipartFile file)
      throws IOException {
    return UserProfileImageDTO.builder()
        .userId(userId)
        .fileData(file.getBytes())
        .contentType(file.getContentType())
        .build();
  }

}
