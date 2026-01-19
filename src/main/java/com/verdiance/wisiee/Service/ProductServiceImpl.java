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
  public void updateProducts(Long formId, List<ProductRequestDTO> newProductRequests) {
    // 1. 기존 데이터와 폼 엔티티 조회
    List<ProductEntity> existingEntities = productJpaRepository.findByForm_Id(formId);
    FormEntity formEntity = formRepository.findById(formId)
        .orElseThrow(() -> new FormNotFoundException(formId));

    int existingSize = existingEntities.size();
    int newSize = newProductRequests.size();

    // 2. 기존 데이터 업데이트 (수정)
    for (int i = 0; i < Math.min(existingSize, newSize); i++) {
      ProductEntity entity = existingEntities.get(i);
      ProductRequestDTO dto = newProductRequests.get(i);

      // 엔티티 자체의 update 메서드 호출 (Dirty Checking)
      entity.update(dto);
    }

    // 3. 새로운 데이터 추가 (Insert)
    if (newSize > existingSize) {
      for (int i = existingSize; i < newSize; i++) {
        ProductRequestDTO dto = newProductRequests.get(i);
        // 매퍼 호출 시 formEntity를 함께 전달
        ProductEntity newEntity = productMapper.toEntity(dto, formEntity);
        productJpaRepository.save(newEntity);
      }
    }
    // 4. 남는 데이터 삭제 (Delete)
    else if (existingSize > newSize) {
      for (int i = newSize; i < existingSize; i++) {
        productJpaRepository.delete(existingEntities.get(i));
      }
    }
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
