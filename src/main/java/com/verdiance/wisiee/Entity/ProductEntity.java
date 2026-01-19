package com.verdiance.wisiee.Entity;

import com.verdiance.wisiee.DTO.Product.ProductDTO;
import com.verdiance.wisiee.DTO.Product.ProductRequestDTO;
import com.verdiance.wisiee.Exception.Stock.OutOfStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "Product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;


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


    public void updateFromDTO(ProductDTO dto) {
        this.productName = dto.getProductName();
        this.productDescript = dto.getProductDescript();
        this.price = dto.getPrice();
        this.productCnt = dto.getProductCnt();
        this.stock = dto.getStock();
        // 필요하다면 stock은 productCnt 기준으로 계산할 수도 있음
    }

    // --- 재고 관리 메서드 ---
    public void decreaseStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new OutOfStockException(productName + " 재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public void update(ProductRequestDTO dto) {
        // 1. 수량 차이 계산 (수정된 총 수량 - 기존 총 수량)
        int countDifference = dto.getProductCnt() - this.productCnt;

        // 2. 기본 정보 업데이트
        this.productName = dto.getProductName();
        this.productDescript = dto.getProductDescript();
        this.price = dto.getPrice();
        this.productCnt = dto.getProductCnt();

        // 3. 재고 반영 (이미 구현된 메서드 활용)
        if (countDifference > 0) {
            this.increaseStock(countDifference);
        } else if (countDifference < 0) {
            // 총 수량을 줄일 때 현재 남은 재고보다 더 많이 줄이려 하면 에러 발생 가능
            this.decreaseStock(Math.abs(countDifference));
        }
    }
}
