package com.verdianc.wisiee.Exception.Form;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class CodeRequiredException extends BaseException {

  public CodeRequiredException() {
    super(ErrorCode.CODE_REQUIRED, "Code 필수 생성 필요");
  }

}
