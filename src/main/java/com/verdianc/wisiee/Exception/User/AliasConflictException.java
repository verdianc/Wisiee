package com.verdianc.wisiee.Exception.User;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class AliasConflictException extends BaseException {
    public AliasConflictException(String alias) {
        super(
                ErrorCode.AliasConflict,
                String.format("'%s' 는 이미 존재하는 닉네임입니다.", alias)
        );
    }
}
