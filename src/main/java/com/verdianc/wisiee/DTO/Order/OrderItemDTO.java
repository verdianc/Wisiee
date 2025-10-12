package com.verdianc.wisiee.DTO.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private int quantity;
    private int orderPrice;
    // 상품 상세 정보 (Product) - 주문 당시 필요했던 이름, 설명만 가져옵니다.
    private String productName;
    private String productDescript;

    // Form 정보
    private Long formId;
    private String formNm;
    private String createUser;
    
}
