package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FormCreateFailedException extends BaseException {
  public FormCreateFailedException(String reason) {
    super(ErrorCode.FORM_CREATE_FAILED, "Form 생성 실패: " + reason);
  }
}