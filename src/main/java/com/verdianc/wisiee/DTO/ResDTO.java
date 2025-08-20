package com.verdianc.wisiee.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResDTO {

  @Schema(description = "응답 코드", example = "200")
  private int ;

  @Schema(description = "응답 메시지", example = "성공")
  private String message;

  @Schema(description = "응답 데이터")
  private T data;

  public static <T> ResDTO<T> success(T data) {
    return ResDTO.<T>builder()
        .code(200)
        .message("성공")
        .data(data)
        .build();
  }

  public static <T> ResDTO<T> success(String message, T data) {
    return ResDTO.<T>builder()
        .code(200)
        .message(message)
        .data(data)
        .build();
  }

  public static <T> ResDTO<T> fail(int code, String message) {
    return ResDTO.<T>builder()
        .code(code)
        .message(message)
        .build();
  }


}
