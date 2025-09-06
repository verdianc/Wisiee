package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.DTO.User.UserResponseDTO;
import com.verdianc.wisiee.Entity.UserInfo;

public class UserMapper {

    public static UserEntity toDTO(UserInfoUpdateDTO userInfoUpdateDTO) {
        if (userInfoUpdateDTO==null) return null;
        return UserEntity.builder()
                .nickNm(userInfoUpdateDTO.getNickNm())
                .build();
    }
}
