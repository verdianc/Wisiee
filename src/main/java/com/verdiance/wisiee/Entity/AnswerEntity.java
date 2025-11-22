package com.verdiance.wisiee.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class AnswerEntity {

  @Id
  @Column(name="answer_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 답변
  private String answer;

  @OneToOne
  @JoinColumn(name = "question_id") // DB 테이블에 inquiry_id 외래 키 컬럼 생성
  private QuestionEntity questionEntity;

  // 게시글 작성자(사용자)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false) // FK 이름 user_id
  private UserEntity user;


}
