package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.facade.UserFacadeService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacadeService userFacadeService;

    @GetMapping("/login/success")
    public ResDTO<OauthDTO> loginSuccess() {
        return new ResDTO<>(userFacadeService.getCurrentUser());
    }


    @PutMapping("/nickname")
    public ResDTO<Void> updateUserNickNm(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        userFacadeService.updateUserNickNm(userInfoUpdateDTO);
        return new ResDTO<>((Void) null);
    }

    @PostMapping("/chkExistNickNm")
    public ResDTO<UserChkExistNickNmDTO> chkExistNickNm(@RequestBody UserChkExistNickNmDTO dto) {
        return new ResDTO<UserChkExistNickNmDTO>(userFacadeService.chkExistNickNm(dto));
    }


//  @PutMapping("/profile-image")
//  public ResDTO<String> updateUserProfileImage(
//      @RequestPart("file") MultipartFile file) throws IOException {
//
//    Long userId = userFacadeService.getCurrentUser().getUserId();
//
//    UserProfileImageDTO dto = new UserProfileImageDTO();
//    dto.setUserId(userId);
//    dto.setFileData(file.getBytes());
//    dto.setContentType(file.getContentType());
//
//    String imageUrl = userFacadeService.updateUserProfileImage(dto);
//    return new ResDTO<>(imageUrl);
//  }

    @PutMapping("/userProfile")
    public ResDTO<Void> updateUserProfileImage(
            @RequestPart(value = "files", required = false) MultipartFile file,
            @RequestPart("user") UserInfoUpdateDTO dto) throws IOException {

        Long userId = userFacadeService.getCurrentUser().getUserId();
        dto.setUserId(userId);
        userFacadeService.updateUserInfo(dto);

        if (file!=null && !file.isEmpty()) {
            dto.setFile(file);
            String imageUrl = userFacadeService.updateUserProfileImage(dto);
        }


        return new ResDTO<>((Void) null);
    }


}
