package com.verdianc.wisiee.Exception.User;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class AddressNotFoundException extends BaseException {
    public AddressNotFoundException() {
        super(
                ErrorCode.AddressNotFound,
                "주소록을 찾을 수 없습니다."
        );
    }
}
