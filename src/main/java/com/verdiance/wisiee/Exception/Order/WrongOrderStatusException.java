package com.verdiance.wisiee.Exception.Order;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class WrongOrderStatusException extends BaseException {
    public WrongOrderStatusException(String orderStatus) {
        super(
                ErrorCode.WRONG_ORDER_STATUS,
                "잘못된 주문 상태 ORDER_STATUS=" + orderStatus
        );
    }
}
