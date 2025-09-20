package com.verdianc.wisiee.Service.Interface;


import com.verdianc.wisiee.DTO.Product.ProductDTO;
import com.verdianc.wisiee.DTO.Product.ProductRequestDTO;
import java.util.List;

public interface ProductService {

  ProductDTO createProduct(ProductRequestDTO productRequestDTO);

  void deleteProductsByFormId(Long formId);

  ProductDTO updateProduct(Long productId, ProductDTO productDTO);

  void deleteProduct(Long productId);

  ProductDTO getProduct(Long productId);

  List<ProductDTO> getProductsByFormId(Long formId);

}
