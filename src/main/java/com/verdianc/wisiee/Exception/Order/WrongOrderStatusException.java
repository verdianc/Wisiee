package com.verdianc.wisiee.Exception.Order;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class WrongOrderStatusException extends BaseException {
    public WrongOrderStatusException(String orderStatus) {
        super(
                ErrorCode.WRONG_ORDER_STATUS,
                "잘못된 주문 상태 ORDER_STATUS=" + orderStatus
        );
    }
}
