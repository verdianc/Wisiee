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
  private final Integer errorCode;
  private final String errorMessage;
  private final long timestamp;

  /**
   * 성공 응답 (기본값을 ErrorCode.SUCCESS로 설정)
   */
  public ResDTO(T data) {
    this.success = true;
    this.data = data;
    this.errorCode = ErrorCode.SUCCESS.getCode(); // 0
    this.errorMessage = ErrorCode.SUCCESS.getMessage(); // "요청이 성공적으로 처리되었습니다."
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> ResDTO<T> success(T data) {
    return new ResDTO<>(data);
  }

  public static ResDTO<Void> success() {
    return new ResDTO<>(null);
  }

  /**
   * 실패 응답 (ErrorCode 기준)
   */
  public ResDTO(ErrorCode errorCode) {
    this.success = false;
    this.data = null;
    ErrorCode target = (errorCode != null) ? errorCode : ErrorCode.INTERNAL_SERVER_ERROR;
    this.errorCode = target.getCode();
    this.errorMessage = target.getMessage();
    this.timestamp = System.currentTimeMillis();
  }

  /**
   * 실패 응답 (메시지 커스터마이징이 필요한 경우)
   */
  public ResDTO(ErrorCode errorCode, String message) {
    this.success = false;
    this.data = null;
    ErrorCode target = (errorCode != null) ? errorCode : ErrorCode.INTERNAL_SERVER_ERROR;
    this.errorCode = target.getCode();
    this.errorMessage = (message != null) ? message : target.getMessage();
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> ResDTO<T> fail(ErrorCode code) {
    return new ResDTO<>(code);
  }

  public static <T> ResDTO<T> fail(ErrorCode code, String message) {
    return new ResDTO<>(code, message);
  }


}
