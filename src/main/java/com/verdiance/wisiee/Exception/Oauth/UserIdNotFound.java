package com.verdiance.wisiee.Exception.Oauth;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class UserIdNotFound extends BaseException {
    public UserIdNotFound(String providerNm, String providerId) {
        super(ErrorCode.USER_ID_NOT_FOUND, "존재하지 않는 Form 입니다. id=" + providerNm + ": " + providerId);
    }
}
