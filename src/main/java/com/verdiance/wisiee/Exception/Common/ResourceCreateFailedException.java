package com.verdiance.wisiee.Exception.Common;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ResourceCreateFailedException extends BaseException {
  public ResourceCreateFailedException(String message) {
    super(ErrorCode.RESOURCE_CREATE_FAILED, message);
  }
}
