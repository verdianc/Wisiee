package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.Faq.FaqDTO;
import com.verdiance.wisiee.Entity.AnswerEntity;
import com.verdiance.wisiee.Entity.QuestionEntity;
import com.verdiance.wisiee.Repository.AnswerRepository;
import com.verdiance.wisiee.Repository.QuestionRepository;
import com.verdiance.wisiee.Service.Interface.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FaqServiceImpl implements FaqService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;

  /**
   * FAQ 등록
   * - user 없음
   * - 파일 없음
   * - 관리자 답변 1개 고정
   */
  @Override
  public FaqDTO createFaq(String question, String answer, Category category) {

    // FAQ 질문 생성 (user = null)
    QuestionEntity q = new QuestionEntity(
        null,
        question,
        null,
        category
    );

    QuestionEntity savedQuestion = questionRepository.save(q);

    // FAQ 답변 생성 (관리자 답변)
    AnswerEntity a = new AnswerEntity(
        answer,
        savedQuestion,
        null   // AdminEntity 필요 없음 (fromAdmin = true는 생성자에서 처리)
    );

    answerRepository.save(a);

    return toDto(savedQuestion, a);
  }

  /**
   * 카테고리별 FAQ 조회
   */
  @Override
  @Transactional(readOnly = true)
  public Page<FaqDTO> getFaqsByCategory(Category category, Pageable pageable) {

    return questionRepository
        .findByCategory(category, pageable)
        .map(q -> toDto(
            q,
            answerRepository
                .findFirstByQuestionIdAndFromAdminTrue(q.getId())
                .orElse(null)
        ));
  }

  /**
   * 제목 기반 FAQ 검색
   */
  @Override
  @Transactional(readOnly = true)
  public Page<FaqDTO> searchFaq(String keyword, Pageable pageable) {

    return questionRepository
        .findByTitleContainingIgnoreCase(keyword, pageable)
        .map(q -> toDto(
            q,
            answerRepository
                .findFirstByQuestionIdAndFromAdminTrue(q.getId())
                .orElse(null)
        ));
  }

  /**
   * FAQ 삭제
   */
  @Override
  public void deleteFaq(Long faqId) {
    questionRepository.deleteById(faqId);
  }

  /**
   * Entity → DTO 변환
   */
  private FaqDTO toDto(QuestionEntity q, AnswerEntity a) {
    return FaqDTO.builder()
        .id(q.getId())
        .question(q.getTitle())
        .answer(a != null ? a.getAnswer() : null)
        .category(q.getCategory())
        .build();
  }
}
