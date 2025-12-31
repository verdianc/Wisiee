package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormService {

  // 생성
  FormDTO createForm(FormRequestDTO request, UserEntity user);
  // 단건 조회
  FormDTO getForm(Long id, String code);


  // 전체 리스트 조회
  Page<FormDTO> getFormList(Pageable pageable);

  // 수정
  FormDTO updateForm(Long id, FormRequestDTO request, UserEntity user);

  // 삭제
  void deleteForm(Long id, UserEntity user);

  // 작성한 폼 목록
  List<FormDTO> getFormsByUser(UserEntity user);

}
