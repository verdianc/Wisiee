package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import java.util.List;

public interface FormService {

  // 생성

  FormDTO createForm(FormRequestDTO request);

  // 단건 조회
  FormDTO getForm(Long id);

  // 전체 리스트 조회
  List<FormDTO> getFormList();

  // 수정
  FormDTO updateForm(Long id, FormRequestDTO request);

  // 삭제
  void deleteForm(Long id);

}
