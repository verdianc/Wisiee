package com.verdiance.wisiee.Exception.Order;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(Long productId) {
        super(
                ErrorCode.PRODUCT_NOT_FOUND,
                "상품을 찾을 수 없습니다. productId=" + productId
        );

    }
}
