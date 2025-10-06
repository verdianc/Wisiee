package com.verdianc.wisiee.facade;

import com.verdianc.wisiee.Common.Enum.OrderStatus;
import com.verdianc.wisiee.Common.Util.CommonUtil;
import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespListDTO;
import com.verdianc.wisiee.Exception.Order.WrongOrderStatusException;
import com.verdianc.wisiee.Service.Interface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacadeService {
    private final CommonUtil commonUtil;
    private final OrderService orderService;

    public Long getUserId() {
        return commonUtil.getUserId();
    }


    public OrderRespDTO createOrder(OrderReqDTO dto) {
        return orderService.createOrder(dto);
    }

    public OrderRespListDTO getSoldOrderList(Long userId) {
        return orderService.getSoldOrderList(userId);
    }

    public void updateOrderStatus(OrderReqDTO dto) {
        OrderStatus orderSTatus = OrderStatus.safeValueOf(dto.getOrderStatus()).orElseThrow(() -> new WrongOrderStatusException(dto.getOrderStatus()));
        orderService.updateOrderStatus(dto.getOrderId(), orderSTatus);
    }
}
