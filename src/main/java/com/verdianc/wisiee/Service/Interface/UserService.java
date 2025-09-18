package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdianc.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdianc.wisiee.DTO.User.AddressBookResponseDTO;
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

    void deleteUser(Long usedId);

    AddressBookRequestDTO createAddressBook(AddressBookRequestDTO dto, Long userId);

    AddressBookListResponseDTO getAddressBook(Long userId);

    AddressBookRequestDTO updateAddressBook(AddressBookRequestDTO dto, Long userId);

    void setDefaultAddress(Long addressId, Long userId);

    void delAddressBook(Long id);

    AddressBookResponseDTO getMainAddress(Long userId);
}
