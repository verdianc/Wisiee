package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class FormFileLimitExceededException extends BaseException {

  public FormFileLimitExceededException() {
    super(ErrorCode.FORM_FILE_LIMIT_EXCEEDED, ErrorCode.FORM_FILE_LIMIT_EXCEEDED.getMessage());
  }

}
