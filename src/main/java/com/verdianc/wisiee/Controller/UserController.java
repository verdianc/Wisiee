package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.facade.UserFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacadeService userFacadeService;

    @PutMapping("/update")
    public ResDTO<Void> updateUserNickNm(@RequestBody UserInfoUpdateDTO userInfoUpdateDTO) {
        userFacadeService.updateUserNickNm(userInfoUpdateDTO);
        return new ResDTO<>(null);
    }

}
