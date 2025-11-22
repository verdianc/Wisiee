package com.verdiance.wisiee.DTO.Qna;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaRequestDTO {

  //문의 제목
  private String title;

  //문의 내용
  private String content;

  //문의 카테고리
  private Category category;

  // 첨부 파일
  private List<FileRequestDTO> files;

}
