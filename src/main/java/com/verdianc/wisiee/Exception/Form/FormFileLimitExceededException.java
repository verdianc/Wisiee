package com.verdianc.wisiee.Exception.Form;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class FormFileLimitExceededException extends BaseException {

  public FormFileLimitExceededException() {
    super(ErrorCode.FORM_FILE_LIMIT_EXCEEDED, ErrorCode.FORM_FILE_LIMIT_EXCEEDED.getMessage());
  }

}
