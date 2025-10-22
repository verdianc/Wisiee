package com.verdiance.wisiee.Exception.Oauth;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class RefreshAccessTokenFail extends BaseException {
    public RefreshAccessTokenFail() {
        super(ErrorCode.REFRESH_ACCESS_TOKEN_FAIL, "ACCESS TOKEN REFRESH에 실패하였습니다.");
    }
}
