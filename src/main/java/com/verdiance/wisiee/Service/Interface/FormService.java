package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;

public interface FormService {

  // 생성
  FormDTO createForm(FormRequestDTO request, UserEntity user);
  // 단건 조회
  FormDTO getForm(Long id, String code);


  // 전체 리스트 조회
  List<FormDTO> getFormList();

  // 수정
  FormDTO updateForm(Long id, FormRequestDTO request, UserEntity user);

  // 삭제
  void deleteForm(Long id, UserEntity user);

}
