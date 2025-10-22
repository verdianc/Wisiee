package com.verdiance.wisiee.DTO.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDTO {

  private Long productId;        // Product 엔티티 PK
  private Long formId;
  private String productName;    // 상품명
  private String productDescript;// 상품 설명
  private int price;             // 가격
  private int productCnt;        // 총 수량
  private int stock;             // 현재 재고

}
