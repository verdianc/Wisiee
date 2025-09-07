package com.verdianc.wisiee.Exception.Oauth;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class RefreshTokenNotFound extends BaseException {
    public RefreshTokenNotFound() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND, "REFRESH TOKEN이 없습니다.");
    }
}
