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


        // ===============================
        // 400 - 폼 코드 누락
        // ===============================
        @ExceptionHandler(CodeRequiredException.class)
        public ResponseEntity<ResDTO<Void>> handleCodeRequiredException(CodeRequiredException e) {
            log.error("[CodeRequiredException] {}", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResDTO.fail(ErrorCode.CODE_REQUIRED, e.getMessage()));
        }

        // ===============================
        // 401 - 세션 없음
        // ===============================
        @ExceptionHandler(SessionUserNotFoundException.class)
        public ResponseEntity<ResDTO<Void>> handleSessionUserNotFoundException(SessionUserNotFoundException e) {
            log.error("[SessionUserNotFoundException] {}", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResDTO.fail(ErrorCode.SESSION_USER_NOT_FOUND, "로그인이 필요합니다."));
        }

        // ===============================
        // 공통 BaseException 처리 (🔥 핵심)
        // ===============================
        @ExceptionHandler(BaseException.class)
        public ResponseEntity<ResDTO<Void>> handleBaseException(BaseException e) {
            ErrorCode errorCode = e.getErrorCode() != null
                ? e.getErrorCode()
                : ErrorCode.INTERNAL_SERVER_ERROR;

            log.error("[BaseException] code={}, message={}",
                errorCode.getCode(), e.getMessage());

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResDTO.fail(errorCode, e.getMessage()));
        }

        // ===============================
        // 404 - 리소스 없음
        // ===============================
        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException e) {
            log.debug("[NoResourceFoundException] {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // ===============================
        // 500 - 알 수 없는 서버 오류
        // ===============================
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ResDTO<Void>> handleException(Exception e) {
            log.error("[UnknownException]", e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResDTO.fail(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "서버 내부 오류가 발생했습니다."
                ));
        }
}