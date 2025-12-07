package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Entity.AdminEntity;

public interface AdminService {

  void login(AdminLoginRequestDTO dto);
  AdminEntity getCurrentAdmin();

}
