package com.verdianc.wisiee.DTO.Form;

import com.verdianc.wisiee.Common.Enum.Category;
import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormRequestDTO {

  //form 생성 응답 사용 가능, 서비스레이어에서 엔티티 대신 사용 가능
  // 판매자 이름
  private String userName;

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



}
