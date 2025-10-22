package com.verdiance.wisiee.Exception.Oauth;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class ProviderNotFound extends BaseException {
    public ProviderNotFound(String providerNm) {
        super(ErrorCode.PROVIDER_NOT_FOUND, "존재하지 않는 Provider 입니다:" + providerNm);
    }
}
