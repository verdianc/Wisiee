package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import com.verdianc.wisiee.Common.Enum.OrderStatus;
import com.verdianc.wisiee.DTO.Order.OrderItemDTO;
import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.Entity.OrderEntity;
import com.verdianc.wisiee.Entity.OrderItemEntity;
import com.verdianc.wisiee.Entity.ProductEntity;
import com.verdianc.wisiee.Entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderEntity toEntity(OrderReqDTO dto, UserEntity user) {
        return OrderEntity.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.valueOf(dto.getOrderStatus()))
                .deliveryOption(DeliveryOption.valueOf(dto.getDeliveryOption()))
                .alias(dto.getAlias())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .recipientNm(dto.getRecipientNm())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

    public static OrderItemEntity toItemEntity(OrderItemDTO dto, OrderEntity order, ProductEntity product) {
        return OrderItemEntity.builder()
                .order(order)
                .product(product)
                .quantity(dto.getQuantity())
                .orderPrice(product.getPrice() * dto.getQuantity())
                .build();
    }

    public static OrderRespDTO toResponse(OrderEntity order, List<OrderItemEntity> items) {
        OrderRespDTO res = new OrderRespDTO();
        res.setOrderId(order.getId());
        res.setUserId(order.getUser().getUserId());
        res.setOrderDate(order.getOrderDate());
        res.setTotalPrice(items.stream().mapToInt(OrderItemEntity::getOrderPrice).sum());
        res.setQuantity(items.stream().mapToInt(OrderItemEntity::getQuantity).sum());
        res.setOrderStatus(order.getOrderStatus().name());
        res.setDeliveryOption(order.getDeliveryOption().name());

        // 아이템 응답 DTO 변환 (필요하면 별도 Mapper)
        res.setItems(items.stream().map(i -> new OrderItemDTO(
                i.getProduct().getId(),
                i.getQuantity(),
                i.getOrderPrice()
        )).collect(Collectors.toList()));

        return res;
    }
}
