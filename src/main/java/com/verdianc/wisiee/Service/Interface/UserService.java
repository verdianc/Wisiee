package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserEntity;

public interface UserService {
    //NickNm Update
    public void updateUserNickNm(UserInfoUpdateDTO updateUserInfo);


    // change profile image
    void updateUserProfileImage(Long userId, Long fileId);
}
