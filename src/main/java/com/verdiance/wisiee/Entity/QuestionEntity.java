package com.verdiance.wisiee.Entity;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.Qna.QuestionRequestDTO;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class QuestionEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<AnswerEntity> answers = new ArrayList<>();


  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  private Category category;

  private boolean closed;

  private boolean isFaq;

  public QuestionEntity(UserEntity user, String title, String content, Category category) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.category = category;
    this.closed = false;
    this.isFaq = false;
  }

  public QuestionEntity(String title, String content, Category category, boolean isFaq) {
    this.user = null;
    this.title = title;
    this.content = content;
    this.category = category;
    this.isFaq = isFaq;
    this.closed = true;
  }

  public void update(QuestionRequestDTO dto) {
    if (dto.getTitle() != null) this.title = dto.getTitle();
    if (dto.getContent() != null) this.content = dto.getContent();
    if (dto.getCategory() != null) this.category = dto.getCategory();
  }

  public void close() {
    this.closed = true;
  }
}
