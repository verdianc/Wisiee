package com.verdiance.wisiee.Exception.Form;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ProductNotFoundException extends BaseException {

  public ProductNotFoundException(Long productId) {
    super(ErrorCode.FORM_NOT_FOUND, "존재하지 않는 Product 입니다. id=" + productId);
  }

}
