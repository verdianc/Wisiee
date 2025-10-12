package com.verdianc.wisiee.Common.Enum;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {
    PAID,       // 입금 확인
    PREP,       // 상품 준비중
    SHIPPED,    // 발송
    TRANSIT,    // 배송중
    DONE,   // 배송 완료
    CANCELED; //주문 취소

    public static Optional<OrderStatus> safeValueOf(String orderStatusStr) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(orderStatusStr))
                .findFirst();
    }
}
