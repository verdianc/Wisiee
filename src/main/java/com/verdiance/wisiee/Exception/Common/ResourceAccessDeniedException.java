package com.verdiance.wisiee.Exception.Common;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ResourceAccessDeniedException extends BaseException {
  public ResourceAccessDeniedException(String message) {
    super(ErrorCode.RESOURCE_ACCESS_DENIED, message);
  }
}
