package com.verdianc.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {
    private String nickNm;
    private String email;
    private String profileImg;
}
