package com.verdiance.wisiee.DTO.Faq;

import com.verdiance.wisiee.Common.Enum.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqRequestDTO {

  private String question;
  private String answer;
  private Category category;

}
