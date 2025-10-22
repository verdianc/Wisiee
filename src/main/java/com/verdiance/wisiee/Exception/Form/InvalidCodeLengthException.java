package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class InvalidCodeLengthException extends BaseException {

  public InvalidCodeLengthException(int length) {
    super(ErrorCode.INVALID_CODE_LENGTH,
        "입력된 코드 길이(" + length + ")가 유효하지 않습니다. " +
            "코드는 5자 이상, 20자 이하만 가능합니다.");
  }

}
