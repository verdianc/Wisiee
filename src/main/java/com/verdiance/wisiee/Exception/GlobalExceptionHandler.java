package com.verdiance.wisiee.Exception;

import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.Exception.Form.CodeRequiredException;
import com.verdiance.wisiee.Exception.User.SessionUserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 폼 생성 관련 예외 처리
    @ExceptionHandler(CodeRequiredException.class)
    public ResponseEntity<ResDTO> handleCodeRequiredException(CodeRequiredException e) {
        log.error("[CodeRequiredException] {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResDTO.fail(ErrorCode.CODE_REQUIRED, e.getMessage()));
    }

    // 세션 관련 예외 처리
    @ExceptionHandler(SessionUserNotFoundException.class)
    public ResponseEntity<ResDTO> handleSessionUserNotFoundException(SessionUserNotFoundException e) {
        log.error("[SessionUserNotFoundException] {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(ResDTO.fail(ErrorCode.SESSION_USER_NOT_FOUND, "로그인이 필요합니다."));
    }

    // 그 외 BaseException을 상속받는 예외 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResDTO> handleBaseException(BaseException e) {
        log.error("[BaseException] code={}, message={}", e.getErrorCode().getCode(), e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResDTO.fail(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
        log.debug("[NoResourceFoundException] {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // 예상치 못한 시스템 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResDTO> handleException(Exception e) {
        log.error("[UnknownException] {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."));
    }
}