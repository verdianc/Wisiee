package com.verdiance.wisiee.DTO.Order;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderRespListDTO {
    public List<OrderRespDTO> orderRespDTOList;
    public int orderCnt;
}
