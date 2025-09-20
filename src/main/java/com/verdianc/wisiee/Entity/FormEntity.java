package com.verdianc.wisiee.Entity;

import com.verdianc.wisiee.Common.Enum.Category;
import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "form")
public class FormEntity extends BaseEntity {

  // 폼전체
  // 폼 공통 정보 기본값 세팅
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false) // FK 이름 user_id
  private UserEntity user;

  // 입장 코드
  private String code;

  // 폼 제목
  private String title;

  // 판매 시작 날짜
  private LocalDate startDate;

  // 판매 종료 날짜
  private LocalDate endDate;

  // 삭제 여부
  private boolean isDeleted;

  // 글 공개 여부
  private boolean isPublic;

  // 수정 버전 관리
  private int version;

  // 제품 카테고리
  @Enumerated(EnumType.STRING)
  private Category category;

  // 제품 설명
  private String description;

  // 재고 29개
  private int totStock;

  // 상품 수량  ex. 30
  private int totCnt;

  private boolean isSoldOut;

  //enum, 상품 배송 옵션
  @Enumerated(EnumType.STRING)
  private DeliveryOption deliveryOption;

  // 연락처
  private String contact;


  // 계좌 번호
  private String account;

  // 예금주명
  private String accName;

  // 은행
  private String bank;


  @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderColumn(name = "sort _index")
  private List<FileEntity> files = new ArrayList<>();

  @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id ASC")
  private List<ProductEntity> fields = new ArrayList<>();


  @Builder
  public FormEntity(
      UserEntity user,
      int totCnt,
      int totStock,
      String code,
      String title,
      LocalDate startDate,
      LocalDate endDate,
      boolean isPublic,
      Category category,
      String description,
      DeliveryOption deliveryOption,
      String contact,
      String account,
      String accName,
      String bank
  ) {
    this.user = user;
    this.totCnt = totCnt;
    this.totStock = totStock;
    this.code = code;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPublic = isPublic;
    this.category = category;
    this.description = description;
    this.deliveryOption = deliveryOption;
    this.contact = contact;
    this.account = account;
    this.accName = accName;
    this.bank = bank;
  }


  public void update(FormRequestDTO dto) {
    this.code = dto.getCode();
    this.title = dto.getTitle();
    this.startDate = dto.getStartDate();
    this.endDate = dto.getEndDate();
    this.isPublic = dto.isPublic();
    this.category = dto.getCategory();
    this.description = dto.getDescription();
    this.deliveryOption = dto.getDeliveryOption();
    this.contact = dto.getContact();
    this.account = dto.getAccount();
    this.accName = dto.getAccName();
    this.bank = dto.getBank();
  }
}
