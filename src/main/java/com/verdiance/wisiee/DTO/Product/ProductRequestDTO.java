package com.verdiance.wisiee.DTO.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

  private Long formId;
  private String productName;
  private String productDescript;
  private int price;
  private int productCnt;

}
