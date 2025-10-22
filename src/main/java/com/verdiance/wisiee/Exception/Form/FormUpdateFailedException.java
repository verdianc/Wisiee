package com.verdiance.wisiee.Exception.Form;


import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FormUpdateFailedException extends BaseException {
  public FormUpdateFailedException(String reason) {
    super(ErrorCode.FORM_UPDATE_FAILED, "Form 수정 실패: " + reason);
  }
}