package com.verdianc.wisiee.Exception.Form;


import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FormUpdateFailedException extends BaseException {
  public FormUpdateFailedException(String reason) {
    super(ErrorCode.FORM_UPDATE_FAILED, "Form 수정 실패: " + reason);
  }
}