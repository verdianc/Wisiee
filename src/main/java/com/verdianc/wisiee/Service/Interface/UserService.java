package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.User.MyPageDTO;
import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserEntity;

public interface UserService {

    UserEntity getUser();

    OauthDTO getCurrentUser();

    public UserChkExistNickNmDTO chkExistNickNm(UserChkExistNickNmDTO dto);

    //NickNm Update
    void updateUserNickNm(UserInfoUpdateDTO updateUserInfo);

    void updateUserProfileImage(Long userId, String url);

    MyPageDTO getMyPage();

    void updateUserProfile(UserInfoUpdateDTO updateUserInfo);
}
