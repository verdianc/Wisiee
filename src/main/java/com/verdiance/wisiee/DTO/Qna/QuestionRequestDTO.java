package com.verdiance.wisiee.DTO.Qna;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionRequestDTO {

  private Long id; // 수정/삭제 시 사용
  private String title;
  private String content;
  private Category category;
  private List<FileRequestDTO> files;

}
