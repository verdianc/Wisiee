package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FormDeleteFailedException extends BaseException {
  public FormDeleteFailedException(Long formId) {
    super(ErrorCode.FORM_DELETE_FAILED, "Form 삭제 실패: id=" + formId);
  }
}