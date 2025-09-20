package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.Product.ProductDTO;
import com.verdianc.wisiee.DTO.Product.ProductRequestDTO;
import com.verdianc.wisiee.Entity.FormEntity;
import com.verdianc.wisiee.Entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


  // ProductRequestDTO -> ProductEntity
  public ProductEntity toEntity(ProductRequestDTO dto, FormEntity form) {
    return ProductEntity.builder()
        .form(form)
        .productName(dto.getProductName())
        .productDescript(dto.getProductDescript())
        .price(dto.getPrice())
        .productCnt(dto.getProductCnt())
        .stock(dto.getProductCnt()) // 초기 재고 = 총 수량
        .build();
  }

  // ProductDTO -> ProductEntity
  public ProductEntity toEntity(ProductDTO dto, FormEntity form) {
    return ProductEntity.builder()
        .id(dto.getProductId())
        .form(form)
        .productName(dto.getProductName())
        .productDescript(dto.getProductDescript())
        .price(dto.getPrice())
        .productCnt(dto.getProductCnt())
        .stock(dto.getStock())
        .build();
  }

  // ProductEntity -> ProductDTO
  public ProductDTO toDTO(ProductEntity entity) {
    return ProductDTO.builder()
        .productId(entity.getId())
        .formId(entity.getForm().getId())
        .productName(entity.getProductName())
        .productDescript(entity.getProductDescript())
        .price(entity.getPrice())
        .productCnt(entity.getProductCnt())
        .stock(entity.getStock())
        .build();
  }

}

