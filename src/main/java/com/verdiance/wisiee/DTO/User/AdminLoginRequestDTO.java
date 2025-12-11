package com.verdiance.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdminLoginRequestDTO {
  private String username;
  private String password;

}
