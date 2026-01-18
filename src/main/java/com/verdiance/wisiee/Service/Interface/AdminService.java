package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Qna.AnswerDTO;
import com.verdiance.wisiee.DTO.Qna.AnswerRequestDTO;
import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Entity.AdminEntity;

public interface AdminService {

    void login(AdminLoginRequestDTO dto);

    AdminEntity getCurrentAdmin(Long adminId);

    AnswerDTO registerAdminAnswer(AnswerRequestDTO dto, Long adminId);

}
