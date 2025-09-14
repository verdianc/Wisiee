package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.Entity.FormEntity;
import com.verdianc.wisiee.Entity.UserEntity;
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
    return FormDTO.builder()
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
  }
}
