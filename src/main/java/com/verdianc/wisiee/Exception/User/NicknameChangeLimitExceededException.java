package com.verdianc.wisiee.Exception.User;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.Exception.BaseException;

public class NicknameChangeLimitExceededException extends BaseException {


  public NicknameChangeLimitExceededException(Long userId, int currentCount) {
    super(
        ErrorCode.NICKNAME_CNT_EXCEEDED,
        String.format("사용자 ID=%d 는 닉네임 변경 횟수(%d회)를 초과했습니다.", userId, currentCount)
    );
  }

}
