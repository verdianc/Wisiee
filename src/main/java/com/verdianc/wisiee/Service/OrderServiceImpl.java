package com.verdianc.wisiee.Service;

import com.verdianc.wisiee.Common.Enum.OrderStatus;
import com.verdianc.wisiee.DTO.Order.OrderItemListDTO;
import com.verdianc.wisiee.DTO.Order.OrderReqDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespDTO;
import com.verdianc.wisiee.DTO.Order.OrderRespListDTO;
import com.verdianc.wisiee.Entity.OrderEntity;
import com.verdianc.wisiee.Entity.OrderItemEntity;
import com.verdianc.wisiee.Entity.ProductEntity;
import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.Order.ProductNotFoundException;
import com.verdianc.wisiee.Exception.User.UserNotFound;
import com.verdianc.wisiee.Mapper.OrderMapper;
import com.verdianc.wisiee.Repository.OrderItemRepository;
import com.verdianc.wisiee.Repository.OrderRepository;
import com.verdianc.wisiee.Repository.ProductJpaRepository;
import com.verdianc.wisiee.Repository.UserRepository;
import com.verdianc.wisiee.Service.Interface.OrderService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    @Transactional
    public OrderRespDTO createOrder(OrderReqDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFound(dto.getUserId()));

        OrderEntity order = OrderMapper.toEntity(dto, user);

        // 아이템 매핑
        OrderEntity finalOrder = order;
        List<OrderItemEntity> items = dto.getItems().stream().map(itemDto -> {
            ProductEntity product = productJpaRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));


            return OrderMapper.toItemEntity(itemDto, finalOrder, product);
        }).collect(Collectors.toList());

        order.getOrderItemEntities().addAll(items);

        // 총합 계산
        // 총합(단가 * 수량) 계산
        int totalPrice = items.stream()
                .mapToInt(i -> i.getOrderPrice() * i.getQuantity())
                .sum();
        int quantity = items.stream().mapToInt(OrderItemEntity::getQuantity).sum();
        order.setTotalInfo(totalPrice, quantity);
        order = orderRepository.save(order);
        orderItemRepository.saveAll(items);

        // 응답 DTO 변환
        OrderRespDTO res = new OrderRespDTO(
                order.getId(),                 // 주문 ID
                user.getUserId(),              // 사용자 ID
                order.getOrderDate(),          // 주문 일시
                totalPrice,                    // 총 금액
                quantity,                      // 총 수량
                order.getOrderStatus().name(), // 주문 상태
                order.getDeliveryOption().name(), // 배송 옵션
                (OrderItemListDTO) dto.getItems()                 // 주문 아이템 목록 (요청 DTO 기준)
        );

        return res;
    }

    @Override
    public OrderRespListDTO getOrderList(Long userId) {
        List<OrderEntity> soldOrderEntity = orderRepository.findOrdersByBuyerId(userId);
        return OrderMapper.toOrderRespListDTO(soldOrderEntity);
    }

    @Override
    public OrderRespListDTO getSoldOrderList(Long userId) {
        List<OrderEntity> soldOrderEntity = orderRepository.findOrdersBySellerId(userId);
        return OrderMapper.toOrderRespListDTO(soldOrderEntity);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
