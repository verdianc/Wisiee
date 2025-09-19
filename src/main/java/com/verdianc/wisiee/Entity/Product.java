package com.verdianc.wisiee.Entity;

import com.verdianc.wisiee.Common.Enum.Category;
import com.verdianc.wisiee.Common.Enum.DeliveryOption;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity(name = "Product")
@Getter
@RequiredArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("formId")
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

    // 상품 총개수 5개
    private int productCnt;

    // 상품 재고 -1 4개
    private int stock;

    // 상품 이름 ex. 민정 인형
    @Column(name = "product_name")
    private String productName;

    // 상품 설명 ex. 민정 인형 핑크옷
    @Column(name = "product_descript")
    private String productDescript;


    // 상품 가격 ex. 민정 인형 가격
    private int price;

}
