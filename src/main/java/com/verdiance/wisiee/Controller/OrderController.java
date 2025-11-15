package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.Facade.OrderFacadeService;
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

    // 주문 생성
    @PostMapping
    public ResDTO<OrderRespDTO> create(@RequestBody OrderReqDTO dto) {
        Long userId = orderFacadeService.getUserId();
        dto.setUserId(userId);
        return new ResDTO<>(orderFacadeService.createOrder(dto));
    }

    // 구매자의 주문 리스트 가져오기
    @GetMapping("/orderList")
    public ResDTO<OrderRespListDTO> getOrderList() {
        Long userId = orderFacadeService.getUserId();
        return new ResDTO<>(orderFacadeService.getOrderList(userId));
    }


    // 판매자가에게 온 주문리스트 조회
    @GetMapping("/soldOrderList")
    public ResDTO<OrderRespListDTO> getSoldOrderList() {
        Long userId = orderFacadeService.getUserId();
        return new ResDTO<>(orderFacadeService.getSoldOrderList(userId));
    }


    // 주문 상태 변경
    @PutMapping("/orderStatus")
    public ResDTO<Void> updateOrderStatus(@RequestBody OrderReqDTO dto) {
        orderFacadeService.updateOrderStatus(dto);
        return new ResDTO<>((Void) null);
    }


    // 주문 취소
    @PutMapping("/cancel")
    public ResDTO<Void> cancelOrder(@RequestBody OrderReqDTO dto) {
        Long userId = orderFacadeService.getUserId();
        dto.setUserId(userId);
        orderFacadeService.cancelOrder(dto);
        return new ResDTO<>((Void) null);
    }

    // 주소지 변경
    @PutMapping("/updateAddress")
    public ResDTO<Void> updateAddress(@RequestBody OrderReqDTO dto) {
        Long userId = orderFacadeService.getUserId();
        dto.setUserId(userId);
        orderFacadeService.updateAddress(dto);
        return new ResDTO<>((Void) null);
    }
}
