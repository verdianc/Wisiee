package com.verdianc.wisiee.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoUpdateDTO {
    private Long userId;
    private String nickNm;
    private String profileImg;
}
