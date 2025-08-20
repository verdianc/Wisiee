package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.FormField;
import com.verdianc.wisiee.Entity.FormFieldId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormFieldJpaRepository extends JpaRepository<FormField, FormFieldId> {
    // formId 기준으로 전부 조회 + order 순으로 정렬
    List<FormField> findByFormFieldId_FormId(Long formId);

}
