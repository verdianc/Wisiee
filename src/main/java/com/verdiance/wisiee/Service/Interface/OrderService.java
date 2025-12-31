package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.DTO.Order.OrderPageRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderRespDTO createOrder(OrderReqDTO dto);

    OrderRespListDTO getOrderList(Long userId);

    OrderPageRespDTO getSoldOrderList(Long userId, Pageable pageable);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);

    void cancelOrder(OrderReqDTO dto);

    void updateAddress(OrderReqDTO dto);

    OrderPageRespDTO getOrdersByFormId(Long userId, Long formId, Pageable pageable);
}
