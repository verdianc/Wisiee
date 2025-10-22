package com.verdiance.wisiee.Exception;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {


  private final ErrorCode errorCode;

  protected BaseException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
}
