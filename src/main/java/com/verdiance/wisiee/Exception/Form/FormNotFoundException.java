package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FormNotFoundException extends BaseException {

  public FormNotFoundException(Long formId) {
    super(ErrorCode.FORM_NOT_FOUND, "존재하지 않는 Form 입니다. id=" + formId);
  }

}
