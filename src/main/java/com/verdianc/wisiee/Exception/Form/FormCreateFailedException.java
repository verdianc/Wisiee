package com.verdianc.wisiee.Exception.Form;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FormCreateFailedException extends BaseException {
  public FormCreateFailedException(String reason) {
    super(ErrorCode.FORM_CREATE_FAILED, "Form 생성 실패: " + reason);
  }
}