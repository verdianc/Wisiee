package com.verdianc.wisiee.Service.Interface;

import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;

public interface OrderService {
    OrderRespDTO createOrder(OrderReqDTO dto);
}
