package com.verdiance.wisiee.Exception;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {


  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode, String message) {
    super(message);
    if (errorCode == null) {
      throw new IllegalArgumentException("ErrorCode must not be null");
    }
    this.errorCode = errorCode;
  }

}
