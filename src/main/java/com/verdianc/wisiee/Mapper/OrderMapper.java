package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import com.verdianc.wisiee.Common.Enum.OrderStatus;
import com.verdianc.wisiee.DTO.Order.OrderItemDTO;
import com.verdianc.wisiee.DTO.Order.OrderItemListDTO;
import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespListDTO;
import com.verdianc.wisiee.Entity.OrderEntity;
import com.verdianc.wisiee.Entity.OrderItemEntity;
import com.verdianc.wisiee.Entity.ProductEntity;
import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.Order.WrongOrderStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderEntity toEntity(OrderReqDTO dto, UserEntity user) {
        return OrderEntity.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.safeValueOf(dto.getOrderStatus()).orElseThrow(() -> new WrongOrderStatusException(dto.getOrderStatus())))
                .deliveryOption(DeliveryOption.valueOf(dto.getDeliveryOption()))
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

    public static OrderRespDTO toOrderRespDTO(OrderEntity order) {
        List<OrderItemDTO> itemDTOS = (List<OrderItemDTO>) order.getOrderItemEntities().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getOrderPrice(),
                        item.getProduct().getProductName(),
                        item.getProduct().getProductDescript(),
                        item.getProduct().getForm().getId(),
                        item.getProduct().getForm().getTitle(),
                        item.getProduct().getForm().getUser().getNickNm()
                ))
                .toList();

        return new OrderRespDTO(
                order.getId(),
                order.getUser().getUserId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getQuantity(),
                order.getOrderStatus().name(),
                order.getDeliveryOption().name(),
                new OrderItemListDTO(itemDTOS, itemDTOS.size())

        );
    }

    public static OrderRespListDTO toOrderRespListDTO(List<OrderEntity> orders) {
        var dtoList = orders.stream()
                .map(OrderMapper::toOrderRespDTO)
                .collect(Collectors.toList());
        return new OrderRespListDTO(dtoList, dtoList.size());
    }
}
