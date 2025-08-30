package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserInfo;

public class UserMapper {

    public static UserInfo toDTO(UserInfoUpdateDTO userInfoUpdateDTO) {
        if (userInfoUpdateDTO==null) return null;
        return UserInfo.builder()
                .nickNm(userInfoUpdateDTO.getNickNm())
                .build();
    }
}
