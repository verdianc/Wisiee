package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.DTO.User.MyPageDTO;
import com.verdianc.wisiee.DTO.User.OauthDTO;
import com.verdianc.wisiee.DTO.User.UserChkExistNickNmDTO;
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
    @Transactional
    public void updateUserNickNm(UserInfoUpdateDTO updateUserInfo) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        // 도메인 로직 호출
        user.changeNickName(updateUserInfo.getNickNm());
    }

    @Override
    public UserChkExistNickNmDTO chkExistNickNm(UserChkExistNickNmDTO dto) {
        if (userRepository.existsByNickNm(dto.getNickNm())) {
            dto.setExistRslt(true);
        }
        return dto;
    }


    @Override
    public UserEntity getUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }
        return userRepository.findById(userId)
                .orElseThrow(SessionUserNotFoundException::new);
    }


    @Override
    public OauthDTO getCurrentUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        return OauthDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickNm(user.getNickNm())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageDTO getMyPage() {
        UserEntity user = getUser();

        int formCount = userRepository.countFormsByUser(user);

        return MyPageDTO.builder()
                .userId(user.getUserId())
                .nickNm(user.getNickNm())
                .email(user.getEmail())
                .profileImgUrl(user.getProfileImgUrl())
                .formCount(formCount)
                .nickChangeLeft(3 - user.getNickChangeCount())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public void updateUserProfile(UserInfoUpdateDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFound(dto.getUserId()));

        // 엔티티 내 메서드로 값 업데이트
        user.updateProfile(dto);
        user.changeNickName(dto.getNickNm());
        userRepository.save(user);
    }

    // TODO : 사용자 폼 리스트 조회 메소드 추가


    @Override
    @Transactional
    public void updateUserProfileImage(Long userId, String url) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        user.changeProfileImage(url);
    }


}
