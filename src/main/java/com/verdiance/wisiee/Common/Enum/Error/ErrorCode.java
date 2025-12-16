package com.verdiance.wisiee.Common.Enum.Error;

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
    FORM_FILE_LIMIT_EXCEEDED(1004, "폼에는 최대 3개의 파일만 첨부할 수 있습니다."),
    CODE_REQUIRED(1005, "비공개 게시글에는 코드가 필수입니다."),
    INVALID_CODE_LENGTH(1006, "코드는 5자 이상, 20자 이하만 가능합니다."),


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
    LOGOUT_FAIL(4004, "Logout에 실패하였습니다."),
    //5000번대: User 관련 에러
    SESSION_USER_NOT_FOUND(5000, "세션에 사용자 정보가 없습니다."),
    USER_NOT_FOUND(5001, "존재하지 않는 사용자 입니다."),
    NICKNAME_CNT_EXCEEDED(5002, "닉네임 변경 횟수가 초과되었습니다."),
    NICKNAME_ALREADY_EXISTS(5003, "이미 존재하는 닉네임입니다."),
    ALIAS_CONFLICT(5004, "이미 존재하는 별칭입니다."),
    ADDRESS_NOT_FOUND(5005, "주소록을 찾을 수 없습니다."),
    DEFAULT_ADDRESS_NOT_FOUND_EXCEPTION(5006, "기본 배송지가 없습니다."),

    //6000번대 : Order 관련 에러
    PRODUCT_NOT_FOUND(6000, "상품을 찾을 수 없습니다"),
    WRONG_ORDER_STATUS(6001, "잘못된 주문 상태"),
    ORDER_NOT_FAOUND(6002, "주문을 찾을 수 없습니다."),
    ORDER_ACCESS_DENIED_EXCEPTION(6003, "주문자와 취소자가 상이합니다."),
    ORDER_CANCELLATION_NOT_ALLOWED_EXCEPTION(6004, "주문이 배송준비까지 진행되어 주문 변경 및 취소 불가합니다."),


    // 7000: Common Resource Error
    RESOURCE_NOT_FOUND(7000, "요청한 리소스를 찾을 수 없습니다."),
    RESOURCE_CREATE_FAILED(7001, "리소스 생성에 실패했습니다."),
    RESOURCE_UPDATE_FAILED(7002, "리소스 수정에 실패했습니다."),
    RESOURCE_DELETE_FAILED(7003, "리소스 삭제에 실패했습니다."),
    RESOURCE_ACCESS_DENIED(7004, "해당 리소스에 대한 접근 권한이 없습니다."),


    // 9000번대: 서버 공통 에러
    INTERNAL_SERVER_ERROR(9000, "서버 내부 오류가 발생했습니다.");

    private final int code;
    private final String message;


}
