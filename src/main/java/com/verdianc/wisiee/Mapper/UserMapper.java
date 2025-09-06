package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserEntity;

public class UserMapper {

    public static UserEntity toDTO(UserInfoUpdateDTO userInfoUpdateDTO) {
        if (userInfoUpdateDTO==null) return null;
        return UserEntity.builder()
                .nickNm(userInfoUpdateDTO.getNickNm())
                .build();
    }
}
