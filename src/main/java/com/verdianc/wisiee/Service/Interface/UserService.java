package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserEntity;

public interface UserService {

    OauthDTO getCurrentUser();


    //NickNm Update
    void updateUserNickNm(UserInfoUpdateDTO updateUserInfo);


    // change profile image
    void updateUserProfileImage(Long userId, Long fileId);
}
