package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.DTO.*;
import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Exception.BaseException;
import com.verdianc.wisiee.Service.Interface.FormService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormFacadeService {

  private final FormService formService;

  public FormDTO createForm(FormRequestDTO request) {
    return formService.createForm(request);
  }

  public FormDTO getForm(Long id) {
    return formService.getForm(id);
  }

  public List<FormDTO> getFormList() {
    return formService.getFormList();
  }

  public FormDTO updateForm(Long id, FormRequestDTO request) {
    return formService.updateForm(id, request);
  }

  public void deleteForm(Long id) {
    formService.deleteForm(id);
  }


}
