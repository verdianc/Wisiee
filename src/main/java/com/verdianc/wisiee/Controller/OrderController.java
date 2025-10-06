package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespListDTO;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.facade.OrderFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderFacadeService orderFacadeService;

    @PostMapping
    public ResDTO<OrderRespDTO> create(@RequestBody OrderReqDTO dto) {
        Long userId = orderFacadeService.getUserId();
        dto.setUserId(userId);
        return new ResDTO<>(orderFacadeService.createOrder(dto));
    }

    @GetMapping("/orderList")
    public ResDTO<OrderRespListDTO> getOrderList() {
        Long userId = orderFacadeService.getUserId();
        return new ResDTO<>(orderFacadeService.getOrderList(userId));
    }


    @GetMapping("/soldOrderList")
    public ResDTO<OrderRespListDTO> getSoldOrderList() {
        Long userId = orderFacadeService.getUserId();
        return new ResDTO<>(orderFacadeService.getSoldOrderList(userId));
    }

    @PutMapping("/orderStatus")
    public ResDTO<Void> updateOrderStatus(@RequestBody OrderReqDTO dto) {
        orderFacadeService.updateOrderStatus(dto);
        return new ResDTO<>((Void) null);
    }
}
