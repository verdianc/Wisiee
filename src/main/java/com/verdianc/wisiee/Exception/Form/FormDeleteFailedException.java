package com.verdianc.wisiee.Exception.Form;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FormDeleteFailedException extends BaseException {
  public FormDeleteFailedException(Long formId) {
    super(ErrorCode.FORM_DELETE_FAILED, "Form 삭제 실패: id=" + formId);
  }
}