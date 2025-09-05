package com.verdianc.wisiee.Common.Enum.Error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  // 1000번대: Form 관련 에러
  FORM_NOT_FOUND(1000, "존재하지 않는 Form 입니다."),
  FORM_CREATE_FAILED(1001, "Form 생성에 실패했습니다."),
  FORM_UPDATE_FAILED(1002, "Form 수정에 실패했습니다."),
  FORM_DELETE_FAILED(1003, "Form 삭제에 실패했습니다."),

  // 2000번대: Validation 관련 에러
  INVALID_REQUEST(2000, "유효하지 않은 요청 값입니다."),
  MISSING_PARAMETER(2001, "필수 요청 파라미터가 누락되었습니다."),

  // 9000번대: 서버 공통 에러
  INTERNAL_SERVER_ERROR(9000, "서버 내부 오류가 발생했습니다.");

  private final int code;
  private final String message;


}
