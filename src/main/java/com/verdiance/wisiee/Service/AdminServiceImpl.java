package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Entity.AdminEntity;
import com.verdiance.wisiee.Exception.Common.ResourceAccessDeniedException;
import com.verdiance.wisiee.Exception.Common.ResourceNotFoundException;
import com.verdiance.wisiee.Repository.AdminRepository;
import com.verdiance.wisiee.Service.Interface.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final HttpSession session;

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

    // 세션 저장
    session.setAttribute(SESSION_KEY, admin.getAdminId());
    log.info("관리자 로그인 성공: {}", admin.getUsername());
  }

  @Override
  public AdminEntity getCurrentAdmin() {
    Long adminId = (Long) session.getAttribute(SESSION_KEY);

    if (adminId == null) {
      throw new ResourceAccessDeniedException("관리자 로그인 세션이 없습니다.");
    }

    return adminRepository.findById(adminId)
        .orElseThrow(() ->
            new ResourceNotFoundException("관리자 정보를 찾을 수 없습니다.")
        );
  }

}
