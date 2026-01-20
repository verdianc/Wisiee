package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.FormEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormJpaRepository extends JpaRepository<FormEntity, Long> {


  boolean existsByCode(String code);

  boolean existsByCodeAndIdNot(String code, Long id);

  List<FormEntity> findByUser(UserEntity user);

  @EntityGraph(attributePaths = {"user"})
  Page<FormEntity> findAll(Pageable pageable);

  @EntityGraph(attributePaths = {"user", "files", "fields"})
  Optional<FormEntity> findById(Long id);

}
