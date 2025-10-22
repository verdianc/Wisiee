package com.verdiance.wisiee.Exception.User;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class AliasConflictException extends BaseException {
    public AliasConflictException(String alias) {
        super(
                ErrorCode.ALIAS_CONFLICT,
                String.format("'%s' 는 이미 존재하는 닉네임입니다.", alias)
        );
    }
}
