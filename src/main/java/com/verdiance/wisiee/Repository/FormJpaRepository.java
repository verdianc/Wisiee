package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.FormEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormJpaRepository extends JpaRepository<FormEntity, Long> {


  boolean existsByCode(String code);

  boolean existsByCodeAndIdNot(String code, Long id);

  List<FormEntity> findByUser_UserId(Long userId);

}
