package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdiance.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdiance.wisiee.DTO.User.MyPageDTO;
import com.verdiance.wisiee.DTO.User.OauthDTO;
import com.verdiance.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdiance.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdiance.wisiee.DTO.User.UserProfileImageDTO;
import com.verdiance.wisiee.Facade.UserFacadeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserFacadeService userFacadeService;

    @GetMapping("/login/success")
    public ResDTO<OauthDTO> loginSuccess() {
        return new ResDTO<>(userFacadeService.getCurrentUser());
    }

    @PostMapping("/logout")
    public ResDTO<Void> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        userFacadeService.logout(request, response, authentication);
        return new ResDTO<>((Void) null);
    }

    //닉네임 중복 확인
    @PostMapping("/check-nickname")
    public ResDTO<UserChkExistNickNmDTO> chkExistNickNm(@RequestBody UserChkExistNickNmDTO dto) {
        return new ResDTO<UserChkExistNickNmDTO>(userFacadeService.chkExistNickNm(dto));
    }


    @PostMapping("/profile-image")
    public ResDTO<String> updateUserProfileImage(
            // TODO : 파일 커스텀 예외 처리하기
            @RequestPart("file") MultipartFile file) throws IOException {
        Long userId = userFacadeService.getUserId();
        UserProfileImageDTO dto = UserProfileImageDTO.fromMultipart(userId, file);

        String imageUrl = userFacadeService.updateUserProfileImage(dto);
        return new ResDTO<>(imageUrl);
    }


    @PutMapping("/profile")
    public ResDTO<Void> updateUserProfile(@RequestBody UserInfoUpdateDTO dto) {

        Long userId = userFacadeService.getUserId();
        dto.setUserId(userId);

        userFacadeService.updateUserInfo(dto);

        return new ResDTO<>((Void) null);
    }

    //사용자 탈퇴
    @DeleteMapping("/user")
    public ResDTO<Void> delUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Long userId = userFacadeService.getUserId();
        userFacadeService.deleteUser(userId);
        userFacadeService.logout(request, response, authentication);
        return new ResDTO<Void>((Void) null);
    }

    //주소록 등록 및 수정
    @PostMapping("/addressBook")
    public ResDTO<AddressBookRequestDTO> createAddressBook(@RequestBody AddressBookRequestDTO dto) {
        Long userId = userFacadeService.getUserId();
        return new ResDTO<AddressBookRequestDTO>(userFacadeService.createAddressBook(dto, userId));
    }

    //주소록 조회
    @GetMapping("/addressBook")
    public ResDTO<AddressBookListResponseDTO> getAddressBook() {
        Long userId = userFacadeService.getUserId();
        return new ResDTO<AddressBookListResponseDTO>(userFacadeService.getAddressBook(userId));
    }

    @PutMapping("/addressBook")
    public ResDTO<AddressBookRequestDTO> updateAddressBook(@RequestBody AddressBookRequestDTO dto) {
        Long userId = userFacadeService.getUserId();
        return new ResDTO<AddressBookRequestDTO>(userFacadeService.updateAddressBook(dto, userId));
    }

    // 기본 배송지 변경
    @PutMapping("/addressBook/default/{id}")
    public ResDTO<Void> setDefaultAddress(@PathVariable("id") Long addressId) {
        Long userId = userFacadeService.getUserId();
        userFacadeService.setDefaultAddress(addressId, userId);
        return new ResDTO<>((Void) null);
    }

    @DeleteMapping("/addressBook/{id}")
    public ResDTO<Void> delAddressBook(@PathVariable("id") Long addressId) {
        userFacadeService.delAddressBook(addressId);
        return new ResDTO<Void>((Void) null);
    }

    @GetMapping("/mypage")
    public ResDTO<MyPageDTO> getMyPage() {
        return new ResDTO<>(userFacadeService.getMyPage());
    }

}
