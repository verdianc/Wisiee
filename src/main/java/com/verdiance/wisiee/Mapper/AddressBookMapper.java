package com.verdiance.wisiee.Mapper;

import com.verdiance.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdiance.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdiance.wisiee.DTO.User.AddressBookResponseDTO;
import com.verdiance.wisiee.Entity.AddressBookEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AddressBookMapper {
    public static AddressBookEntity toEntity(AddressBookRequestDTO dto, UserEntity user) {

        return AddressBookEntity.builder()
                .alias(dto.getAlias())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .phoneNumber(dto.getPhoneNumber())
                .recipientNm(dto.getRecipientNm())
                .defaultAddressYn(dto.isDefaultAddress())
                .user(user)
                .build();
    }

    // Entity -> ResponseDTO
    public static AddressBookResponseDTO toResponseDTO(AddressBookEntity entity) {
        return AddressBookResponseDTO.builder()
                .id(entity.getId())
                .alias(entity.getAlias())
                .zipcode(entity.getZipcode())
                .address(entity.getAddress())
                .detailAddress(entity.getDetailAddress())
                .phoneNumber(entity.getPhoneNumber())
                .recipientNm(entity.getRecipientNm())
                .defaultAddress(entity.getDefaultAddressYn())
                .build();
    }

    // 리스트 + count/limitExceeded 포함
    public static AddressBookListResponseDTO toListDTO(List<AddressBookEntity> entities) {
        List<AddressBookResponseDTO> dtoList = entities.stream()
                .map(AddressBookMapper::toResponseDTO)
                .toList();

        return AddressBookListResponseDTO.builder()
                .addressBooks(dtoList)
                .count(dtoList.size())
                .limitExceeded(dtoList.size() > 3)
                .build();
    }

    public static AddressBookEntity buildUpdatedEntity(AddressBookRequestDTO dto, AddressBookEntity entity) {
        return AddressBookEntity.builder()
                .id(entity.getId())          // PK 유지
                .user(entity.getUser())      // 기존 User 참조 유지
                .alias(dto.getAlias())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .recipientNm(dto.getRecipientNm())
                .phoneNumber(dto.getPhoneNumber())
                .defaultAddressYn(dto.isDefaultAddress())
                .build();
    }
}
