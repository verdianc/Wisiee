package com.verdiance.wisiee.DTO.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressBookRequestDTO {
    private Long id;
    private String alias;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String recipientNm;
    private String phoneNumber;
    private boolean defaultAddress;
    private boolean isLimitExceeded;

}
