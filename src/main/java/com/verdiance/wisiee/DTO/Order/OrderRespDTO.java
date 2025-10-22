package com.verdiance.wisiee.DTO.Order;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRespDTO {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private int totalPrice;
    private int quantity;
    private String orderStatus;
    private String deliveryOption;
    private OrderItemListDTO items;
}
