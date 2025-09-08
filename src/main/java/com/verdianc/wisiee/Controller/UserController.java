package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.DTO.User.UserProfileImageDTO;
import com.verdianc.wisiee.facade.UserFacadeService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
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

        @PutMapping("/update")
        public ResDTO<Void> updateUserNickNm(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
            userFacadeService.updateUserNickNm(userInfoUpdateDTO);
            return new ResDTO<>((Void) null);
        }

        @PutMapping("/profile-image/{id}")
        public ResDTO<FileDTO> updateUserProfileImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file,
            @RequestPart("meta") FileRequestDTO fileRequest) throws IOException {

            UserProfileImageDTO dto = new UserProfileImageDTO();
            dto.setUserId(id);
            dto.setFileRequest(fileRequest);
            dto.setFileData(file.getBytes());
            dto.setContentType(file.getContentType());

            FileDTO result = userFacadeService.updateUserProfileImage(dto);
            return new ResDTO<>(result);
        }


    }
