package com.verdiance.wisiee.Exception.User;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class SessionUserNotFoundException extends BaseException {

    public SessionUserNotFoundException() {
        super(ErrorCode.SESSION_USER_NOT_FOUND, "세션에 사용자 정보가 없습니다.");
    }

}
