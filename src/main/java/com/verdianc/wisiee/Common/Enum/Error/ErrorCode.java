package com.verdianc.wisiee.Common.Enum.Error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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


    // 3000번대: File 관련 에러
    FILE_NOT_FOUND(3000, "존재하지 않는 File 입니다."),
    FILE_UPLOAD_FAILED(3001, "File 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(3002, "File 삭제에 실패했습니다."),
    FILE_READ_FAILED(3003, "File 조회에 실패했습니다."),

    //4000번대: Oauth 관련 에러
    PROVIDER_NOT_FOUND(4000, "존재하지 않는 Provider 입니다."),
    USER_ID_NOT_FOUND(4001, "User ID를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(4002, "REFRESH TOKEN이 없습니다."),
    REFRESH_ACCESS_TOKEN_FAIL(4003, "ACCESS TOKEN REFRESH에 실패하였습니다."),

    //5000번대: User 관련 에러
    SESSION_USER_NOT_FOUND(5000, "세션에 사용자 정보가 없습니다."),
    USER_NOT_FOUND(5001, "존재하지 않는 사용자 입니다."),


    // 9000번대: 서버 공통 에러
    INTERNAL_SERVER_ERROR(9000, "서버 내부 오류가 발생했습니다.");

    private final int code;
    private final String message;


}
