package com.verdiance.wisiee.DTO.Order;

import com.verdiance.wisiee.DTO.User.AddressBookResponseDTO;
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
    private AddressBookResponseDTO addressDto;
    private int totalPrice;
    private int quantity;
    private String orderStatus;
    private String deliveryOption;
    private OrderItemListDTO items;
}
