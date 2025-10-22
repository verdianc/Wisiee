package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;

public interface OrderService {
    OrderRespDTO createOrder(OrderReqDTO dto);

    OrderRespListDTO getOrderList(Long userId);

    OrderRespListDTO getSoldOrderList(Long userId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    void cancelOrder(OrderReqDTO dto);

    void updateAddress(OrderReqDTO dto);
}
