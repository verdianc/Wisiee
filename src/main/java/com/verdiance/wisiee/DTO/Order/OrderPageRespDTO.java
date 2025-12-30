package com.verdiance.wisiee.DTO.Order;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderPageRespDTO {
    private List<OrderRespDTO> orders; // 데이터 목록
    private boolean hasNext;           // 다음 페이지 존재 여부
    private int page;
    private long totalCount;
}