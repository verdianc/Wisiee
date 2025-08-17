package com.verdianc.wisiee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class FormEntity {

  // 폼전체
  // 폼 공통 정보 기본값 세팅

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


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

  // 폼 생성 날짜
  private LocalDateTime createdAt;

  // 폼 수정 날짜
  private LocalDateTime updatedAt;


  // 삭제 여부
  private boolean isDeleted;

  // 글 공개 여부
  private boolean isPublic;


  // 수정 버전 관리
  private int version;

  // 제품 카테고리
  private String category;


  // 판매 가격
  private int price;


  // 제품 설명
  private String description;

  // 재고
  private int stock;

  //enum, 상품 배송 옵션
  private String deliveryOption;

  // 연락처
  private String contact;


  // 계좌 번호
  private String account;

  // 하위 항목들
  private List<FormField> fields;

  // 이미지
  private List<FileInfo> files;


}
