package com.verdiance.wisiee.Exception.Order;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(Long orderId) {
        super(
                ErrorCode.ORDER_NOT_FAOUND,
                "주문을 찾을 수 없습니다. OrderId = " + orderId
        );
    }
}
