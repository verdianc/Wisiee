package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Exception.User.SessionUserNotFoundException;
import com.verdianc.wisiee.Repository.UserRepository;
import com.verdianc.wisiee.Service.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public void updateUserNickNm(UserInfoUpdateDTO updateUserInfo) {
        // 세션에서 userId 꺼내기
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }


        userRepository.updateNickNm(userId, updateUserInfo.getNickNm());
    }
}
