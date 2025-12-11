package com.verdiance.wisiee.DTO.Qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDTO {
  private Long questionId;
  private String answer;
}

