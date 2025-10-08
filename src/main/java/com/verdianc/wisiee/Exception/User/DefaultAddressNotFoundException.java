package com.verdianc.wisiee.Exception.User;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class DefaultAddressNotFoundException extends BaseException {
    public DefaultAddressNotFoundException() {
        super(
                ErrorCode.DEFAULT_ADDRESS_NOT_FOUND_EXCEPTION,
                "기본 배송지가 없습니다."
        );
    }
}
