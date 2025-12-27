package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.AnswerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

  Optional<AnswerEntity> findFirstByQuestionIdAndFromAdminTrue(Long questionId);


}
