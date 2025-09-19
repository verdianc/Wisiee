package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.DTO.Product.ProductDTO;
import com.verdianc.wisiee.Entity.FormEntity;
import com.verdianc.wisiee.Entity.Product;
import com.verdianc.wisiee.Entity.UserEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FormMapper {


  public FormEntity toEntity(FormRequestDTO dto, UserEntity user) {
    // 먼저 form 객체를 만든다.
    FormEntity form = FormEntity.builder()
        .user(user)
        .code(dto.getCode())
        .title(dto.getTitle())
        .startDate(dto.getStartDate())
        .endDate(dto.getEndDate())
        .isPublic(dto.isPublic())
        .category(dto.getCategory())
        .description(dto.getDescription())
        .deliveryOption(dto.getDeliveryOption())
        .contact(dto.getContact())
        .account(dto.getAccount())
        .accName(dto.getAccName())
        .bank(dto.getBank())
        .build();

    // ProductRequestDTO -> Product 엔티티 변환
    if (dto.getProducts() != null) {
      dto.getProducts().forEach(p -> {
        Product product = Product.builder()
            .form(form) // 이제 정상적으로 인식됨
            .productName(p.getProductName())
            .productDescript(p.getProductDescript())
            .price(p.getPrice())
            .productCnt(p.getProductCnt())
            .stock(p.getProductCnt()) // 초기 재고 = 총 개수
            .build();

        form.getFields().add(product);
      });
    }

    return form;
  }


  public FormDTO toDTO(FormEntity entity) {
    FormDTO formDTO = FormDTO.builder()
        .id(entity.getId())
        .nickName(entity.getUser().getNickNm())
        .code(entity.getCode())
        .totCnt(entity.getTotCnt())
        .totStock(entity.getTotStock())
        .title(entity.getTitle())
        .startDate(entity.getStartDate())
        .endDate(entity.getEndDate())
        .isPublic(entity.isPublic())
        .category(entity.getCategory())
        .description(entity.getDescription())
        .isSoldOut(entity.isSoldOut())
        .deliveryOption(entity.getDeliveryOption())
        .contact(entity.getContact())
        .account(entity.getAccount())
        .accName(entity.getAccName())
        .bank(entity.getBank())
        .build();

    // Product 엔티티 -> ProductDTO 변환
    if (entity.getFields() != null) {
      List<ProductDTO> products = entity.getFields().stream()
          .map(p -> ProductDTO.builder()
              .productId(p.getId())
              .productName(p.getProductName())
              .productDescript(p.getProductDescript())
              .price(p.getPrice())
              .productCnt(p.getProductCnt())
              .stock(p.getStock())
              .build()
          )
          .toList();
      formDTO.setProducts(products);
    }

    return formDTO;
  }

}
