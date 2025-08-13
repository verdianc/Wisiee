package com.verdianc.wisiee.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FormEntity {


  // 폼전체
  // 폼 공통 정보 기본값 세팅

  private String userName;

  private String code;

  private String title;

  private LocalDate startDate;
  private LocalDate endDate;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private boolean isDeleted;

  private boolean isPublic;

  private int version;

  private int price;

  private String description;

  private int stock;

  //enum
  private String deliveryOption;

  private String account;

  private List<FormField> fields;


}
