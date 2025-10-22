package com.verdiance.wisiee.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "address_book",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "alias"})
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AddressBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "집", "회사", "부모님댁" 별칭
    private String alias;

    // 우편번호
    private String zipcode;

    // 기본 주소 (도로명/지번)
    private String address;

    // 상세 주소 (동/호, 건물명 등)
    @Column(name = "detail_address")
    private String detailAddress;

    // 수신인명
    @Column(name = "recipient_name")
    private String recipientNm;

    //번호
    @Column(name = "phone_number")
    private String phoneNumber;

    //기본 여부
    @Column(name = "default_address_yn")
    private Boolean defaultAddressYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void chgDefault(boolean defaultAddress) {
        this.defaultAddressYn = defaultAddress;
    }


}
