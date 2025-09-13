package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Exception.File.FileUploadFailedException;
import com.verdianc.wisiee.Infrastructure.S3.S3Port;
import com.verdianc.wisiee.Service.Interface.UserService;
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

    private final UserService userService;
    private final S3Port s3Port;

    public OauthDTO getCurrentUser() {
        return userService.getCurrentUser();
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

}
