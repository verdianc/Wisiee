package com.verdiance.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoUpdateDTO {

    private Long userId;
    private String nickNm;
    private String email;
}
