package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UseEntity;

public class UserMapper {

    public static UseEntity toDTO(UserInfoUpdateDTO userInfoUpdateDTO) {
        if (userInfoUpdateDTO==null) return null;
        return UseEntity.builder()
                .nickNm(userInfoUpdateDTO.getNickNm())
                .build();
    }
}
