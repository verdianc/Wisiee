package com.verdianc.wisiee.Exception.Order;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(Long productId) {
        super(
                ErrorCode.AddressNotFound,
                "상품을 찾을 수 없습니다. productId=" + productId
        );

    }
}
