package com.verdiance.wisiee.Exception.User;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.Exception.BaseException;

public class NicknameAlreadyExistsException extends BaseException {


  public NicknameAlreadyExistsException(String nickname) {
    super(
        ErrorCode.NICKNAME_ALREADY_EXISTS,
        String.format("'%s' 는 이미 존재하는 닉네임입니다.", nickname)
    );
  }

}
