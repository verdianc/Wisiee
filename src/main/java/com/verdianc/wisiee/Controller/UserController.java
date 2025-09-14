package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdianc.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.facade.UserFacadeService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @PutMapping("/profile/nickname")
    public ResDTO<Void> updateUserNickNm(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        userFacadeService.updateUserNickNm(userInfoUpdateDTO);
        return new ResDTO<>((Void) null);
    }

    //닉네임 중복 확인
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

    //사용자 정보 수정
    @PutMapping("/profile")
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

    //사용자 탈퇴
    @DeleteMapping("/user")
    public ResDTO<Void> delUser() {
        Long userId = userFacadeService.getCurrentUser().getUserId();
        userFacadeService.deleteUser(userId);
        return new ResDTO<Void>((Void) null);
    }

    //주소록 등록 및 수정
    @PostMapping("/addressBook")
    public ResDTO<AddressBookRequestDTO> createAddressBook(@RequestBody AddressBookRequestDTO dto) {
        Long userId = userFacadeService.getCurrentUser().getUserId();
        return new ResDTO<AddressBookRequestDTO>(userFacadeService.createAddressBook(dto, userId));
    }

    //주소록 조회
    @GetMapping("/addressBook")
    public ResDTO<AddressBookListResponseDTO> getAddressBook() {
        Long userId = userFacadeService.getCurrentUser().getUserId();
        return new ResDTO<AddressBookListResponseDTO>(userFacadeService.getAddressBook(userId));
    }

    @PutMapping("/addressBook")
    public ResDTO<AddressBookRequestDTO> updateAddressBook(@RequestBody AddressBookRequestDTO dto) {
        Long userId = userFacadeService.getCurrentUser().getUserId();
        return new ResDTO<AddressBookRequestDTO>(userFacadeService.updateAddressBook(dto, userId));
    }

    @PutMapping("/addressBook/default/{id}")
    public ResDTO<Void> setDefaultAddress(@PathVariable("id") Long addressId) {
        Long userId = userFacadeService.getCurrentUser().getUserId();
        userFacadeService.setDefaultAddress(addressId, userId);
        return new ResDTO<>((Void) null);
    }

    @DeleteMapping("/addressBook/{id}")
    public ResDTO<Void> delAddressBook(@PathVariable("id") Long addressId) {
        userFacadeService.delAddressBook(addressId);
        return new ResDTO<Void>((Void) null);
    }

}
