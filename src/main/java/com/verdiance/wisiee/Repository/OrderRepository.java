package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.Entity.OrderEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select distinct o from OrderEntity o " +
            "join fetch o.orderItemEntities oi " +
            "join fetch oi.product p " +
            "join fetch p.form f " +
            "where f.user.id = :sellerId " +
            "order by o.orderDate desc")
    List<OrderEntity> findOrdersBySellerId(@Param("sellerId") Long sellerId);

    @Modifying
    @Transactional
    @Query("update OrderEntity o set o.orderStatus = :status where o.id = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId,
                          @Param("status") OrderStatus status);

    @Query("select distinct o from OrderEntity o " +
            "join fetch o.orderItemEntities oi " +
            "join fetch oi.product p " +
            "join fetch p.form f " +
            "where o.user.id = :buyerId " +
            "order by o.orderDate desc")
    List<OrderEntity> findOrdersByBuyerId(@Param("buyerId") Long sellerId);

    Optional<OrderEntity> findByIdAndDelYnFalse(Long id);

}
