package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.DTO.User.UserResponseDTO;
import com.verdianc.wisiee.Entity.UserInfo;

public class UserMapper {
    public static UserResponseDTO toDTO(UserInfo userInfo) {
        if (userInfo==null) return null;
        return UserResponseDTO.builder()
                .email(userInfo.getEmail())
                .nickNm(userInfo.getNickNm())
                .profileImg(userInfo.getProfileImgUrl())
                .build();
    }

    public static UserInfo toDTO(UserInfoUpdateDTO userInfoUpdateDTO) {
        if (userInfoUpdateDTO==null) return null;
        return UserInfo.builder()
                .nickNm(userInfoUpdateDTO.getNickNm())
                .build();
    }
}
