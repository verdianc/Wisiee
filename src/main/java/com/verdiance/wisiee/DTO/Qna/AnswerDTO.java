package com.verdiance.wisiee.DTO.Qna;

import com.verdiance.wisiee.Entity.AnswerEntity;
import java.time.LocalDate;
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
  private LocalDate createdAt;


  public static AnswerDTO from(AnswerEntity e) {
    AnswerDTOBuilder builder = AnswerDTO.builder()
        .id(e.getId())
        .questionId(e.getQuestion().getId())
        .content(e.getAnswer())
        .fromAdmin(e.isFromAdmin())
        .createdAt(e.getCreatedAt());

    if (e.isFromAdmin() && e.getAdmin() != null) {
      builder.senderId(e.getAdmin().getAdminId())
          .senderNickNm(e.getAdmin().getUsername());
    }

    else if (e.getUser() != null) {
      builder.senderId(e.getUser().getUserId())
          .senderNickNm(e.getUser().getNickNm());
    }

    return builder.build();
  }
}
