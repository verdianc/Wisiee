package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.Entity.QuestionEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

  // 1. 일반 유저용: 본인이 작성한 질문 목록 조회 (FAQ 제외)
  @EntityGraph(attributePaths = {"answers", "user"})
  Page<QuestionEntity> findByUserAndIsFaqFalse(UserEntity user, Pageable pageable);

  // 2. FAQ용: 카테고리별 조회 (isFaq가 true인 것만 + 답변 포함)
  @EntityGraph(attributePaths = {"answers"})
  Page<QuestionEntity> findByCategoryAndIsFaqTrue(Category category, Pageable pageable);

  // 3. FAQ용: 제목 검색 (isFaq가 true인 것만 + 답변 포함)
  @EntityGraph(attributePaths = {"answers"})
  Page<QuestionEntity> findByTitleContainingIgnoreCaseAndIsFaqTrue(String title, Pageable pageable);

  // 기존 메서드
  Page<QuestionEntity> findByUser(UserEntity user, Pageable pageable);

}
