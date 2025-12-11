package com.verdiance.wisiee.DTO.Qna;

import com.verdiance.wisiee.Entity.AnswerEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {

  private Long id;
  private Long questionId;
  private String content;
  private Long senderId;
  private String senderNickNm;
  private boolean fromAdmin;
  private LocalDateTime createdAt;

  public static AnswerDTO from(AnswerEntity e) {
    return AnswerDTO.builder()
        .id(e.getId())
        .questionId(e.getQuestion().getId())
        .content(e.getAnswer())
        .senderId(e.getUser().getUserId())
        .senderNickNm(e.getUser().getNickNm())
        .fromAdmin(e.isFromAdmin())
        .createdAt(e.getCreatedAt())
        .build();
  }
}
