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
import com.verdianc.wisiee.Exception.Order.OrderAccessDeniedException;
import com.verdianc.wisiee.Exception.Order.OrderCancellationNotAllowedException;
import com.verdianc.wisiee.Exception.Order.OrderNotFoundException;
import com.verdianc.wisiee.Exception.Order.ProductNotFoundException;
import com.verdianc.wisiee.Exception.User.UserNotFound;
import com.verdianc.wisiee.Mapper.OrderMapper;
import com.verdianc.wisiee.Repository.OrderItemRepository;
import com.verdianc.wisiee.Repository.OrderRepository;
import com.verdianc.wisiee.Repository.ProductJpaRepository;
import com.verdianc.wisiee.Repository.UserRepository;
import com.verdianc.wisiee.Service.Interface.OrderService;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
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

            product.decreaseStock(itemDto.getQuantity());
            productJpaRepository.save(product);
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
                new OrderItemListDTO(dto.getItems(), dto.getItems().size())            // 주문 아이템 목록 (요청 DTO 기준)
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

    @Override
    public void cancelOrder(OrderReqDTO dto) {
        //Order 상태 변경 확인
        OrderEntity order = chkOrderModi(dto);
        // 주문 상태 변경
        order.setDel();
        orderRepository.save(order);
    }

    @Override
    public void updateAddress(OrderReqDTO dto) {
        //배송지 변경 가능 확인
        OrderEntity order = chkOrderModi(dto);
        //배송지 변경
        order.modiAddress(dto);

        orderRepository.save(order);
    }

    private OrderEntity chkOrderModi(OrderReqDTO dto) {
        //1.  기존 주문 조회
        OrderEntity order = orderRepository.findByIdAndDelYnFalse(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(dto.getOrderId()));

        //2. 해당 Order 주문자와 접속자의 userId 동일 확인
        if (!order.getUser().getUserId().equals(dto.getUserId())) {
            throw new OrderAccessDeniedException(dto.getOrderId(), dto.getUserId());
        }

        //3. ORDER STATUS가 PREP인지 확인
        Set<OrderStatus> allowed = EnumSet.of(OrderStatus.PAID, OrderStatus.PREP);
        if (!allowed.contains(order.getOrderStatus())) {
            throw new OrderCancellationNotAllowedException(dto.getOrderId());
        }

        return order;

    }
}
