package com.verdianc.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoUpdateDTO {
    private Long userId;
    private String nickNm;
    private String profileImg;
}
