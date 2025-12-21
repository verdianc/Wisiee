package com.verdiance.wisiee.DTO.Faq;

import com.verdiance.wisiee.Common.Enum.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FaqDTO {

  private Long id;
  private String question;
  private String answer;
  private Category category;

}
