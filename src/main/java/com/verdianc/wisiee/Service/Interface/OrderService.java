package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.Common.Enum.OrderStatus;
import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespListDTO;

public interface OrderService {
    OrderRespDTO createOrder(OrderReqDTO dto);

    OrderRespListDTO getSoldOrderList(Long userId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
