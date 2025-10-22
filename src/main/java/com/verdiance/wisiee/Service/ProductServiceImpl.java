package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.Product.ProductDTO;
import com.verdiance.wisiee.DTO.Product.ProductRequestDTO;
import com.verdiance.wisiee.Entity.FormEntity;
import com.verdiance.wisiee.Entity.ProductEntity;
import com.verdiance.wisiee.Exception.Form.FormNotFoundException;
import com.verdiance.wisiee.Exception.Form.ProductNotFoundException;
import com.verdiance.wisiee.Mapper.ProductMapper;
import com.verdiance.wisiee.Repository.FormJpaRepository;
import com.verdiance.wisiee.Repository.ProductJpaRepository;
import com.verdiance.wisiee.Service.Interface.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductJpaRepository productJpaRepository;
  private final FormJpaRepository formRepository;
  private final ProductMapper productMapper;

  @Override
  @Transactional
  public ProductDTO createProduct(ProductRequestDTO productRequestDTO) {
    FormEntity form = formRepository.findById(productRequestDTO.getFormId())
        .orElseThrow(() -> new FormNotFoundException(productRequestDTO.getFormId()));

    ProductEntity productEntity = productMapper.toEntity(productRequestDTO, form);
    ProductEntity saved = productJpaRepository.save(productEntity);

    return productMapper.toDTO(saved);
  }

  @Override
  @Transactional
  public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
    ProductEntity productEntity = productJpaRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    productEntity.updateFromDTO(productDTO); // 엔티티 안에 update 메서드 정의해두면 깔끔
    return productMapper.toDTO(productEntity);
  }

  @Override
  @Transactional
  public void deleteProduct(Long productId) {
    productJpaRepository.deleteById(productId);
  }

  @Override
  @Transactional
  public void deleteProductsByFormId(Long formId) {
    productJpaRepository.deleteByForm_Id(formId);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDTO getProduct(Long productId) {
    ProductEntity productEntity = productJpaRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
    return productMapper.toDTO(productEntity);
  }

  @Override
  @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByFormId(Long formId) {
      return productJpaRepository.findByForm_Id(formId).stream()
        .map(productMapper::toDTO)
        .toList();
  }


}
