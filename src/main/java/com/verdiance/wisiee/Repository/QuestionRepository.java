package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.Entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {


  Page<QuestionEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  Page<QuestionEntity> findByCategory(
      Category category,
      Pageable pageable
  );


}
