package com.verdiance.wisiee.DTO.Form;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.Common.Enum.DeliveryOption;
import com.verdiance.wisiee.DTO.Product.ProductDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FormDTO {

  private Long id;

  // 판매자 이름
  private String nickName;

  // 총 수량
  private int totCnt;

  // 총 재고
  private int totStock;

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

  // 제품 설명
  private String description;

  private boolean isSoldOut;

  //enum, 상품 배송 옵션
  private DeliveryOption deliveryOption;

  // 연락처
  private String contact;

  // 계좌 번호
  private String account;

  //예금주
  private String accName;

  //은행 이름
  private String bank;

  private List<ProductDTO> products;

  private List<String> imageUrls;


}
