package com.verdiance.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthDTO {
  private Long userId;
  private String nickNm;
  private String email;
}
