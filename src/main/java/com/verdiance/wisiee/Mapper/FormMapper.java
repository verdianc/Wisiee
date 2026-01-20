package com.verdiance.wisiee.Mapper;

import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.DTO.Product.ProductDTO;
import com.verdiance.wisiee.Entity.FormEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FormMapper {


  public FormEntity toEntity(FormRequestDTO dto, UserEntity user) {
    return FormEntity.builder()
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

    // 1. FileEntity -> URL 문자열로 변환하여 추가
    if (entity.getFiles() != null) {
      List<String> imageUrls = entity.getFiles().stream()
          .map(file -> String.format("https://%s.s3.amazonaws.com/%s",
              file.getBucket(),
              file.getObjectKey()))
          .toList();
      formDTO.setImageUrls(imageUrls);
    }

    // 2. Product 엔티티 -> ProductDTO 변환 (기존 로직)
    if (entity.getFields() != null) {
      List<ProductDTO> products = entity.getFields().stream()
          .map(p -> ProductDTO.builder()
              .productId(p.getId())
              .formId(entity.getId())
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
