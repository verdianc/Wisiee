package com.verdiance.wisiee.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResDTO<T> {

  private final boolean success;
  private final T data;
  private final Integer errorCode;   // 숫자 코드만 노출
  private final String errorMessage;
  private final long timestamp;

  // 성공 응답
  public ResDTO(T data) {
    this.success = true;
    this.data = data;
    this.errorCode = null;
    this.errorMessage = null;
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> ResDTO<T> success(T data) {
    return new ResDTO<>(data);
  }


  public static ResDTO<Void> success() {
    return new ResDTO<>(null);
  }


  // 실패 응답 (ErrorCode 하나만 받을 때)
  public ResDTO(ErrorCode errorCode) {
    this.success = false;
    this.data = null;
    // null이면 9000번 에러를 기본으로 사용
    ErrorCode temp = (errorCode != null) ? errorCode : ErrorCode.INTERNAL_SERVER_ERROR;
    this.errorCode = temp.getCode();
    this.errorMessage = temp.getMessage();
    this.timestamp = System.currentTimeMillis();
  }

  // 실패 응답 (ErrorCode랑 직접 쓴 메세지랑 같이 받을 때)
  public ResDTO(ErrorCode errorCode, String customMessage) {
    this.success = false;
    this.data = null;
    // null이면 9000번 에러 기본값 사용
    if (errorCode != null) {
      this.errorCode = errorCode.getCode();
      this.errorMessage = (customMessage != null) ? customMessage : errorCode.getMessage();
    } else {
      this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
      this.errorMessage = (customMessage != null) ? customMessage : ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
    }
    this.timestamp = System.currentTimeMillis();
  }


  public static <T> ResDTO<T> fail(ErrorCode code) {
    return new ResDTO<>(code);
  }

  public static <T> ResDTO<T> fail(ErrorCode code, String message) {
    return new ResDTO<>(code, message);
  }


}
