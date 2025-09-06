package com.verdianc.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoUpdateDTO {
    private String nickNm;
    private String email;
    private String profileImg;
}
