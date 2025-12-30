package com.verdiance.wisiee.Facade;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.Common.Util.CommonUtil;
import com.verdiance.wisiee.DTO.Order.OrderPageRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;
import com.verdiance.wisiee.Exception.Order.WrongOrderStatusException;
import com.verdiance.wisiee.Service.Interface.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public OrderPageRespDTO getSoldOrderList(Long userId, Pageable pageable) {
        return orderService.getSoldOrderList(userId, pageable);
    }

    public OrderRespListDTO getOrderList(Long userId) {
        return orderService.getOrderList(userId);
    }

    public void updateOrderStatus(OrderReqDTO dto) {
        OrderStatus orderSTatus = OrderStatus.safeValueOf(dto.getOrderStatus()).orElseThrow(() -> new WrongOrderStatusException(dto.getOrderStatus()));
        orderService.updateOrderStatus(dto.getOrderId(), orderSTatus);
    }

    public void cancelOrder(OrderReqDTO dto) {
        orderService.cancelOrder(dto);
    }

    public void updateAddress(OrderReqDTO dto) {
        orderService.updateAddress(dto);
    }

    public OrderPageRespDTO getOrdersByFormId(Long userId, Long formId, Pageable pageable) {
        return orderService.getOrdersByFormId(userId, formId, pageable);
    }
}
