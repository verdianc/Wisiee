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

  // 실패 응답
  public ResDTO(ErrorCode errorCode) {
    this.success = false;
    this.data = null;
    this.errorCode = errorCode.getCode();
    this.errorMessage = errorCode.getMessage();
    this.timestamp = System.currentTimeMillis();
  }

  // 실패 응답
  public ResDTO(ErrorCode errorCode, String customMessage) {
    this.success = false;
    this.data = null;
    this.errorCode = errorCode.getCode();
    this.errorMessage = customMessage;
    this.timestamp = System.currentTimeMillis();
  }


  public static <T> ResDTO<T> fail(ErrorCode code) {
    return new ResDTO<>(code);
  }

  public static <T> ResDTO<T> fail(ErrorCode code, String message) {
    return new ResDTO<>(code, message);
  }


}
