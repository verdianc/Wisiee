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
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AnswerEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="answer_id")
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String answer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", nullable = false)
  private QuestionEntity question;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;   // 관리자/운영자

  // ==== 생성자 ====
  public AnswerEntity(String answer, QuestionEntity question, UserEntity user, boolean fromAdmin) {
    this.answer = answer;
    this.question = question;
    this.user = user;
    this.fromAdmin = fromAdmin;
  }

  private boolean fromAdmin;

  // ==== update ====
  public void update(String newAnswer) {
    if (newAnswer != null) {
      this.answer = newAnswer;
    }
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_id")
  private AdminEntity admin;

  public AnswerEntity(String answer, QuestionEntity question, AdminEntity admin) {
    this.answer = answer;
    this.question = question;
    this.admin = admin;
    this.fromAdmin = true;
  }
}
