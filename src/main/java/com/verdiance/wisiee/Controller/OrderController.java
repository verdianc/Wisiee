package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.Order.OrderPageRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.Facade.OrderFacadeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderFacadeService orderFacadeService;

    // 주문 생성
    @PostMapping
    public ResDTO<OrderRespDTO> create(@RequestBody OrderReqDTO dto, HttpSession session) {
        Long userId = orderFacadeService.getUserId(session);
        dto.setUserId(userId);
        return new ResDTO<>(orderFacadeService.createOrder(dto));
    }

    // 구매자의 주문 리스트 가져오기
    @GetMapping("/orderList")
    public ResDTO<OrderRespListDTO> getOrderList(HttpSession session) {
        Long userId = orderFacadeService.getUserId(session);
        return new ResDTO<>(orderFacadeService.getOrderList(userId));
    }


    // 주문 상태 변경
    @PutMapping("/orderStatus")
    public ResDTO<Void> updateOrderStatus(@RequestBody OrderReqDTO dto) {
        orderFacadeService.updateOrderStatus(dto);
        return new ResDTO<>((Void) null);
    }


    // 주문 취소
    @PutMapping("/cancel")
    public ResDTO<Void> cancelOrder(@RequestBody OrderReqDTO dto, HttpSession session) {
        Long userId = orderFacadeService.getUserId(session);
        dto.setUserId(userId);
        orderFacadeService.cancelOrder(dto);
        return new ResDTO<>((Void) null);
    }

    // 주소지 변경
    @PutMapping("/updateAddress")
    public ResDTO<Void> updateAddress(@RequestBody OrderReqDTO dto, HttpSession session) {
        Long userId = orderFacadeService.getUserId(session);
        dto.setUserId(userId);
        orderFacadeService.updateAddress(dto);
        return new ResDTO<>((Void) null);
    }

    /**
     * 주문 목록 조회 (통합형)
     * 1. formId가 있으면 -> 특정 폼의 주문만 조회
     * 2. formId가 없으면 -> 판매자의 모든 주문 조회
     */
    @GetMapping
    public ResDTO<OrderPageRespDTO> getOrders(
            @RequestParam(required = false) Long formId, // ★ 여기가 핵심 (필수 아님)
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpSession session
    ) {
        Long userId = orderFacadeService.getUserId(session);
        OrderPageRespDTO result;

        if (formId!=null) {
            result = orderFacadeService.getOrdersByFormId(userId, formId, pageable);
        } else {
            result = orderFacadeService.getSoldOrderList(userId, pageable);
        }

        return new ResDTO<>(result);
    }
}
