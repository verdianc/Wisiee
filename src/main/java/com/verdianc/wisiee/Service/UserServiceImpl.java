package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.User.SessionUserNotFoundException;
import com.verdianc.wisiee.Exception.User.UserNotFound;
import com.verdianc.wisiee.Repository.UserRepository;
import com.verdianc.wisiee.Service.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    @Transactional
    public void updateUserProfileImage(Long userId, Long fileId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFound(userId));

        user.changeProfileImage(userId);
    }


}
