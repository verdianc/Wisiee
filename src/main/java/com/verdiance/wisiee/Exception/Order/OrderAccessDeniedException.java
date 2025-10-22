package com.verdiance.wisiee.Exception.Order;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class OrderAccessDeniedException extends BaseException {
    public OrderAccessDeniedException(Long orderId, Long userID) {
        super(ErrorCode.ORDER_ACCESS_DENIED_EXCEPTION, "주문자와 취소자가 상이합니다. orderId = " + orderId + " userId= " + userID);
    }
}
