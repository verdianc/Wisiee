package com.verdiance.wisiee.Exception.Common;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ResourceNotFoundException extends BaseException {
  public ResourceNotFoundException(String message) {
    super(ErrorCode.RESOURCE_NOT_FOUND, message);
  }
}