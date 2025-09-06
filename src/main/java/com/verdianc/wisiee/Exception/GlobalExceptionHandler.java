package com.verdianc.wisiee.Exception;

import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.Exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ResDTO> handleBaseException(BaseException e) {
    log.error("[BusinessException] code={}, message={}", e.getErrorCode().getCode(), e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST) // 필요에 따라 HttpStatus 커스터마이즈
        .body(ResDTO.fail(e.getErrorCode(), e.getMessage()));
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResDTO> handleException(Exception e) {
    log.error("[UnknownException] {}", e.getMessage(), e);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResDTO.fail(ErrorCode.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."));
  }
}