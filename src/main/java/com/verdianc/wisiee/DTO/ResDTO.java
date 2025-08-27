package com.verdianc.wisiee.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.verdianc.wisiee.Common.Enum.Error.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResDTO {

  // API 요청 성공 여부
  private final boolean success;

  // 요청 결과
  private final Object data;

  // 실패할 경우에만 표시
  private final ErrorCode errorCode;
  private final String errorMessage;

  // API 요청 응답 시간
  private final long timestamp;

  //ex
  // success : true
  // data : [ 파일이 추가되었습니다, url]
  // errorcode : 1002
  // errMsg : "유효하지 않은 파일 형식입니다"

  // 성공 응답 팩토리
  public static ResDTO ok(Object data) {
    return ResDTO.<Void>builder()
        .success(true)
        .data(data)
        .timestamp(System.currentTimeMillis())
        .build();
  }


  // 실패 응답 팩토리
  public static ResDTO fail(ErrorCode code, String message) {
    return ResDTO.<Void>builder()
        .success(false)
        .errorCode(code)
        .errorMessage(message)
        .timestamp(System.currentTimeMillis())
        .build();
  }

}
