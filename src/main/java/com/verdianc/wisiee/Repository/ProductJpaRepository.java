package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

  void deleteByForm_Id(Long formId);


  List<ProductEntity> findByForm_Id(Long formId);

}
