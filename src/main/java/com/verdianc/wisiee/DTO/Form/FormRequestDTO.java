package com.verdianc.wisiee.DTO.Form;

import com.verdianc.wisiee.Common.Enum.Category;
import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormRequestDTO {

  // 입장 코드
  private String code;

  // 폼 제목
  private String title;

  // 판매 시작 날짜
  private LocalDate startDate;

  // 판매 종료 날짜
  private LocalDate endDate;

  // 글 공개 여부
  private boolean isPublic;

  // 제품 카테고리
  private Category category;

  // 판매 가격
  private int price;

  // 제품 설명
  private String description;

  //enum, 상품 배송 옵션
  private DeliveryOption deliveryOption;

  // 연락처
  private String contact;


  // 계좌 번호
  private String account;

  // 첨부 파일
  private List<FileRequestDTO> files;


}
