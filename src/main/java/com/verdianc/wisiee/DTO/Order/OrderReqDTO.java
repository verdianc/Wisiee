package com.verdianc.wisiee.DTO.Order;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReqDTO {
    private Long userId;
    private String orderStatus;
    private List<OrderItemDTO> items;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String recipientNm;
    private String phoneNumber;
    private String deliveryOption; // ENUM 값 문자열로
}
