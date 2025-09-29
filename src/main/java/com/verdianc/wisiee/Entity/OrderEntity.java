package com.verdianc.wisiee.Entity;

import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import com.verdianc.wisiee.Common.Enum.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 product인지랑, 재고, 주소록, 유저 정보, 배송옵션(폼에서 가져오기)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // 주문 상품 - orderItemEntity
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Builder.Default
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();


    // 주문 날짜, 시간까지 포함해서
    private LocalDateTime orderDate;

    // 주문 총 금액
    private int totalPrice;

    // 주문 총 개수
    private int quantity;

    // 배송 상태 enum
    private OrderStatus orderStatus;

    // 배송 옵션 enum
    private DeliveryOption deliveryOption;


    // 배송지
    // "집", "회사", "부모님댁" 별칭
    private String alias;

    // 우편번호
    private String zipcode;

    // 기본 주소 (도로명/지번)
    private String address;

    // 상세 주소 (동/호, 건물명 등)
    @Column(name = "detail_address")
    private String detailAddress;

    // 수신인명
    @Column(name = "recipient_name")
    private String recipientNm;

    //번호
    @Column(name = "phone_number")
    private String phoneNumber;

    public void setTotalInfo(int totalPrice, int quantity) {
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

}
