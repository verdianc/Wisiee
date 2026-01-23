package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Common.Enum.OrderStatus;
import com.verdiance.wisiee.Entity.OrderEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // TODO : queryDSL 변환 or facade로 쪼개기 고려하기
    @Query("select distinct o from OrderEntity o " +
            "join o.orderItemEntities oi " +
            "join oi.product p " +
            "join p.form f " +
            "where f.user.id = :sellerId")
    // OrderBy는 Pageable에 맡김
    Page<OrderEntity> findOrdersBySellerId(
            @Param("sellerId") Long sellerId,
            Pageable pageable
    );

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

    Optional<OrderEntity> findById(Long id);

    @Query("select distinct o from OrderEntity o " +
            "join o.orderItemEntities oi " +
            "join oi.product p " +
            "join p.form f " +
            "where f.id = :formId " +
            "and f.user.id = :sellerId")
    Page<OrderEntity> findOrdersByFormIdAndSellerId(
            @Param("formId") Long formId,
            @Param("sellerId") Long sellerId,
            Pageable pageable
    );

}
