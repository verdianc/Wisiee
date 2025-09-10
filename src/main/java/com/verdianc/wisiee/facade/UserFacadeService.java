package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.DTO.User.UserProfileImageDTO;
import com.verdianc.wisiee.Service.Interface.FileService;
import com.verdianc.wisiee.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final FileService fileService;

    public OauthDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

    public void updateUserNickNm(UserInfoUpdateDTO userInfoUpdateDTO) {
        userService.updateUserNickNm(userInfoUpdateDTO);
    }


    // 프로필 이미지 업데이트
    public FileDTO updateUserProfileImage(UserProfileImageDTO dto) {
        FileDTO fileDTO = fileService.createFile(
            dto.getUserId(),
            dto.getFileRequest(),
            dto.getFileData(),
            dto.getContentType()
        );

        userService.updateUserProfileImage(dto.getUserId(), fileDTO.getId());

        return fileDTO;
    }
}
