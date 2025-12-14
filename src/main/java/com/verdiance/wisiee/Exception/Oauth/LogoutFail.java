package com.verdiance.wisiee.Exception.Oauth;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class LogoutFail extends BaseException {
    public LogoutFail() {
        super(ErrorCode.LOGOUT_FAIL, "Logout에 실패하였습니다.");
    }
}
