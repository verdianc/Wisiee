package com.verdiance.wisiee.DTO.Qna;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.File.FileDTO;
import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import com.verdiance.wisiee.Entity.QuestionEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
public class QuestionDTO {

  private Long id;
  private String nickName;
  private String title;
  private String content;
  private Category category;
  private boolean closed;
  private List<FileDTO> files;
  private List<AnswerDTO> answers;
  private LocalDate createdAt;

  public static QuestionDTO from(QuestionEntity e) {
    return QuestionDTO.builder()
        .id(e.getId())
        .nickName(e.getUser().getNickNm())
        .title(e.getTitle())
        .content(e.getContent())
        .category(e.getCategory())
        .closed(e.isClosed())
        .createdAt(e.getCreatedAt())
        .build();
  }
}

