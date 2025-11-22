package com.verdiance.wisiee.Entity;

import com.verdiance.wisiee.Common.Enum.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class QuestionEntity extends BaseEntity {

  //TODO : 비공개 원칙인데 flag를 따로 추가해야하는지 고민

  @Id
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 게시글 작성자(사용자)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false) // FK 이름 user_id
  private UserEntity user;

  //문의 게시글 제목
  private String title;

  //문의 게시글 내용
  private String content;


  //문의 카테고리
  @Enumerated(EnumType.STRING)
  private Category category;

  // 문의 종료 여부
  private boolean closed;

  // answerId값 참조
  @OneToOne(mappedBy = "inquiry", cascade = CascadeType.ALL) // mappedBy: AnswerEntity의 필드 이름
  private AnswerEntity answer;





}
