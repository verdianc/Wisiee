package com.verdianc.wisiee.DTO.Order;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemListDTO {
    public List<OrderItemDTO> orderItemDTOList;
    public int cnt;

}
