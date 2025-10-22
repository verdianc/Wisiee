package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class CodeRequiredException extends BaseException {

  public CodeRequiredException() {
    super(ErrorCode.CODE_REQUIRED, "Code 필요");
  }

}
