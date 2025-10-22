package com.verdiance.wisiee.Exception.Order;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class OrderCancellationNotAllowedException extends BaseException {
    public OrderCancellationNotAllowedException(Long orderId) {
        super(ErrorCode.ORDER_CANCELLATION_NOT_ALLOWED_EXCEPTION, "주문이 배송준비까지 진행되어 주문 변경 및 취소 불가합니다. OrderId= " + orderId);
    }
}
