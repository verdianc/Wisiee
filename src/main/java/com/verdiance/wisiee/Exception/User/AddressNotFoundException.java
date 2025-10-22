package com.verdiance.wisiee.Exception.User;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class AddressNotFoundException extends BaseException {
    public AddressNotFoundException() {
        super(
                ErrorCode.ADDRESS_NOT_FOUND,
                "주소록을 찾을 수 없습니다."
        );
    }
}
