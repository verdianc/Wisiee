package com.verdiance.wisiee.Exception.Common;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ResourceDeleteFailedException extends BaseException {
  public ResourceDeleteFailedException(String message) {
    super(ErrorCode.RESOURCE_DELETE_FAILED, message);
  }
}
