package com.verdiance.wisiee.Exception.Common;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ResourceUpdateFailedException extends BaseException {
  public ResourceUpdateFailedException(String message) {
    super(ErrorCode.RESOURCE_UPDATE_FAILED, message);
  }
}
