package com.verdiance.wisiee.Facade;

import com.verdiance.wisiee.Common.Util.CommonUtil;
import com.verdiance.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdiance.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdiance.wisiee.DTO.User.AddressBookResponseDTO;
import com.verdiance.wisiee.DTO.User.OauthDTO;
import com.verdiance.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdiance.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdiance.wisiee.Exception.File.FileUploadFailedException;
import com.verdiance.wisiee.Infrastructure.S3.S3Port;
import com.verdiance.wisiee.Service.Interface.UserService;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacadeService {

    private final CommonUtil commonUtil;
    private final UserService userService;
    private final S3Port s3Port;

    public OauthDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

    public Long getUserId() {
        return commonUtil.getUserId();
    }

    public void updateUserNickNm(UserInfoUpdateDTO userInfoUpdateDTO) {
        userService.updateUserNickNm(userInfoUpdateDTO);
    }

    public UserChkExistNickNmDTO chkExistNickNm(UserChkExistNickNmDTO dto) {
        return userService.chkExistNickNm(dto);
    }


    // 프로필 이미지 업데이트
    public String updateUserProfileImage(UserInfoUpdateDTO dto) {
        String objectKey = "profile/" + UUID.randomUUID();
        try {
            S3Port.PutResult put = s3Port.put(objectKey, dto.getFileData(), dto.getContentType(), Map.of());
            String url = s3Port.presignGet(objectKey, put.versionId(), Duration.ofDays(7));

            // 👇 엔티티 직접 안 건드리고 서비스에 위임
            userService.updateUserProfileImage(dto.getUserId(), url);

            return url;
        } catch (Exception e) {
            throw new FileUploadFailedException("프로필 이미지 업로드 실패: " + e.getMessage());
        }
    }

    public void updateUserInfo(UserInfoUpdateDTO dto) {
        userService.updateUserProfile(dto);
    }

    public void deleteUser(Long usedId) {
        userService.deleteUser(usedId);
    }

    public AddressBookRequestDTO createAddressBook(AddressBookRequestDTO dto, Long userId) {
        return userService.createAddressBook(dto, userId);
    }

    public AddressBookListResponseDTO getAddressBook(Long userId) {
        return userService.getAddressBook(userId);
    }

    public AddressBookRequestDTO updateAddressBook(AddressBookRequestDTO dto, Long userId) {
        return userService.updateAddressBook(dto, userId);
    }

    public void setDefaultAddress(Long addressId, Long userId) {
        userService.setDefaultAddress(addressId, userId);
    }

    public void delAddressBook(Long addressId) {
        userService.delAddressBook(addressId);
    }

    public AddressBookResponseDTO getMainAddress(Long userId) {
        return userService.getMainAddress(userId);
    }

}
