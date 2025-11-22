package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {


  //한 페이지에 게시글 5개씩 페이지네이션, 제목으로 검색 기능
  Page<QuestionEntity> findByTitle(String title, Pageable pageable);


}
