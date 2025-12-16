package com.verdiance.wisiee.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressBookResponseDTO {
    private Long id;
    private String alias;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String recipientNm;
    private String phoneNumber;
    private boolean defaultAddress;

    public AddressBookResponseDTO(String zipcode, String address, String detailAddress, String recipientNm, String phoneNumber) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.recipientNm = recipientNm;
        this.phoneNumber = phoneNumber;
    }
}
