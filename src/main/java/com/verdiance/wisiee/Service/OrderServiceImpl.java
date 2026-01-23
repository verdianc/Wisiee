package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.DTO.Order.OrderItemDTO;
import com.verdiance.wisiee.DTO.Order.OrderPageRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderReqDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespDTO;
import com.verdiance.wisiee.DTO.Order.OrderRespListDTO;
import com.verdiance.wisiee.Entity.OrderEntity;
import com.verdiance.wisiee.Entity.OrderItemEntity;
import com.verdiance.wisiee.Entity.ProductEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Order.OrderAccessDeniedException;
import com.verdiance.wisiee.Exception.Order.OrderCancellationNotAllowedException;
import com.verdiance.wisiee.Exception.Order.OrderNotFoundException;
import com.verdiance.wisiee.Exception.Order.ProductNotFoundException;
import com.verdiance.wisiee.Exception.User.UserNotFound;
import com.verdiance.wisiee.Mapper.OrderMapper;
import com.verdiance.wisiee.Repository.OrderItemRepository;
import com.verdiance.wisiee.Repository.OrderRepository;
import com.verdiance.wisiee.Repository.ProductJpaRepository;
import com.verdiance.wisiee.Repository.UserRepository;
import com.verdiance.wisiee.Service.Interface.OrderService;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        OrderRespDTO res = OrderMapper.toOrderRespDTO(order);            // 주문 아이템 목록 (요청 DTO 기준)


        return res;
    }

    @Override
    public OrderRespListDTO getOrderList(Long userId) {
        List<OrderEntity> soldOrderEntity = orderRepository.findOrdersByBuyerId(userId);
        return OrderMapper.toOrderRespListDTO(soldOrderEntity);
    }

    @Override
    public OrderPageRespDTO getSoldOrderList(Long userId, Pageable pageable) {
        Page<OrderEntity> soldOrderEntity = orderRepository.findOrdersBySellerId(userId, pageable);
        return OrderMapper.toOrderSliceRespDTO(soldOrderEntity);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }

    @Transactional
    @Override
    public void cancelOrder(OrderReqDTO dto) {
        //Order 상태 변경 확인
        OrderEntity order = chkOrderModi(dto);

        for (OrderItemDTO orderItem : dto.getItems()) {

            // 상품 조회 (Pessimistic Lock이 필요할 수도 있음)
            ProductEntity product = productJpaRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(orderItem.getProductId()));

            //재고 증가
            product.increaseStock(orderItem.getQuantity());
        }
        // 주문 상태 변경
        order.setDel();


    }

    @Override
    public void updateAddress(OrderReqDTO dto) {
        //배송지 변경 가능 확인
        OrderEntity order = chkOrderModi(dto);
        //배송지 변경
        order.modiAddress(dto);

        orderRepository.save(order);
    }

    @Override
    public OrderPageRespDTO getOrdersByFormId(Long userId, Long formId, Pageable pageable) {
        Page<OrderEntity> orderEntities = orderRepository.findOrdersByFormIdAndSellerId(formId, userId, pageable);

        return OrderMapper.toOrderSliceRespDTO(orderEntities);
    }

    private OrderEntity chkOrderModi(OrderReqDTO dto) {
        //1.  기존 주문 조회
        OrderEntity order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(dto.getOrderId()));

        //2. 해당 Order 주문자와 접속자의 userId 동일 확인
        if (!order.getUser().getUserId().equals(dto.getUserId())) {
            throw new OrderAccessDeniedException(dto.getOrderId(), dto.getUserId());
        }

        //3. ORDER STATUS가 PREP인지 확인
        Set<OrderStatus> allowed = EnumSet.of(OrderStatus.ORDERED, OrderStatus.PAID, OrderStatus.PREP);
        if (!allowed.contains(order.getOrderStatus())) {
            throw new OrderCancellationNotAllowedException(dto.getOrderId());
        }

        return order;

    }
}
