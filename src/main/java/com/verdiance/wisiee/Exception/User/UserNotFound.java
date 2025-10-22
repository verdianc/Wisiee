package com.verdiance.wisiee.Exception.User;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class UserNotFound extends BaseException {
    public UserNotFound(Long userId) {
        super(ErrorCode.USER_NOT_FOUND, "존재하지 않는 사용자 입니다. id=" + userId);
    }
}
