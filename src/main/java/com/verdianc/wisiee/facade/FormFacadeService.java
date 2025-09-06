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

  public ResDTO createForm(FormRequestDTO request) {
    try {
      FormDTO created = formService.createForm(request);
      return ResDTO.ok(created);
    } catch (BaseException e) {
      return ResDTO.fail(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      return ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "폼 생성 중 알 수 없는 오류 발생");
    }
  }


  public ResDTO getForm(Long id) {
    try {
      FormDTO dto = formService.getForm(id);
      return ResDTO.ok(dto);
    } catch (BaseException e) {
      return ResDTO.fail(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      return ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "폼 조회 중 알 수 없는 오류 발생");
    }
  }


  public ResDTO getFormList() {
    try {
      List<FormDTO> list = formService.getFormList();
      return ResDTO.ok(list);
    } catch (BaseException e) {
      return ResDTO.fail(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      return ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "폼 리스트 조회 중 알 수 없는 오류 발생");
    }
  }


  public ResDTO updateForm(Long id, FormRequestDTO request) {
    try {
      FormDTO updated = formService.updateForm(id, request);
      return ResDTO.ok(updated);
    } catch (BaseException e) {
      return ResDTO.fail(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      return ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "폼 수정 중 알 수 없는 오류 발생");
    }
  }


  public ResDTO deleteForm(Long id) {
    try {
      formService.deleteForm(id);
      return ResDTO.ok("삭제되었습니다.");
    } catch (BaseException e) {
      return ResDTO.fail(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      return ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "폼 삭제 중 알 수 없는 오류 발생");
    }
  }

}
