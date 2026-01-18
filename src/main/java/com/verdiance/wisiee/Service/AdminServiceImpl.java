package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.Qna.AnswerDTO;
import com.verdiance.wisiee.DTO.Qna.AnswerRequestDTO;
import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Entity.AdminEntity;
import com.verdiance.wisiee.Exception.Common.ResourceAccessDeniedException;
import com.verdiance.wisiee.Exception.Common.ResourceNotFoundException;
import com.verdiance.wisiee.Oauth.Jwt.JwtTokenProvider;
import com.verdiance.wisiee.Repository.AdminRepository;
import com.verdiance.wisiee.Service.Interface.AdminService;
import com.verdiance.wisiee.Service.Interface.AnswerServie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AnswerServie answerService;

    private static final String SESSION_KEY = "adminId";

    @Override
    @Transactional
    public void login(AdminLoginRequestDTO dto) {

        AdminEntity admin = adminRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("관리자 계정을 찾을 수 없습니다.")
                );

        if (!admin.matchPassword(dto.getPassword())) {
            throw new ResourceAccessDeniedException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(admin.getAdminId(), "ADMIN");

        log.info("관리자 로그인 성공 및 토큰 발급: {}", admin.getUsername());
    }

    @Override
    public AdminEntity getCurrentAdmin(Long adminId) {

        if (adminId==null) {
            throw new ResourceAccessDeniedException("관리자 로그인 세션이 없습니다.");
        }

        return adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("관리자 정보를 찾을 수 없습니다.")
                );
    }

    @Override
    @Transactional
    public AnswerDTO registerAdminAnswer(AnswerRequestDTO dto, Long adminId) {

        AdminEntity admin = getCurrentAdmin(adminId); // 세션에서 조회

        return answerService.registerAnswerByAdmin(dto, admin);
    }


}
