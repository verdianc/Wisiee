package com.verdianc.wisiee.Exception.User;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String providerNm, String providerId) {
        super(ErrorCode.USER_NOT_FOUND, "존재하지 않는 Form 입니다. id=" + providerNm + ": " + providerId);
    }

}
