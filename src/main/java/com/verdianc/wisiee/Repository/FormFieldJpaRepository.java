package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.FormField;
import com.verdianc.wisiee.Entity.FormFieldId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormFieldJpaRepository extends JpaRepository<FormField, FormFieldId> {
}
