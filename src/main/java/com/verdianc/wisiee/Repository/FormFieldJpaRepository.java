package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.Product;
import com.verdianc.wisiee.Entity.FormFieldId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldJpaRepository extends JpaRepository<Product, FormFieldId> {
    // formId 기준으로 전부 조회 + order 순으로 정렬
    List<Product> findByFormFieldId_FormId(Long formId);

}
