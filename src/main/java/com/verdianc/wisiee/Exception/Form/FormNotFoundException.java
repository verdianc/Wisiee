package com.verdianc.wisiee.Exception.Form;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FormNotFoundException extends BaseException {

  public FormNotFoundException(Long formId) {
    super(ErrorCode.FORM_NOT_FOUND, "존재하지 않는 Form 입니다. id=" + formId);
  }

}
