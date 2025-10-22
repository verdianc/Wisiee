package com.verdiance.wisiee.Exception.Oauth;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class RefreshTokenNotFound extends BaseException {
    public RefreshTokenNotFound() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND, "REFRESH TOKEN이 없습니다.");
    }
}
