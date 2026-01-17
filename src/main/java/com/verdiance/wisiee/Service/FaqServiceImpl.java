package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.Common.Enum.Error.ErrorCode;
import com.verdiance.wisiee.DTO.Faq.FaqDTO;
import com.verdiance.wisiee.Entity.AdminEntity;
import com.verdiance.wisiee.Entity.AnswerEntity;
import com.verdiance.wisiee.Entity.QuestionEntity;
import com.verdiance.wisiee.Exception.BaseException;
import com.verdiance.wisiee.Repository.AdminRepository;
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
  private final AdminRepository adminRepository;



  @Override
  @Transactional
  public FaqDTO createFaq(String question, String answer, Category category) {

    // 1. FAQ 질문 생성 (UserEntity는 null)
    QuestionEntity q = new QuestionEntity(
        question,  // title
        question,  // content
        category,
        true       // isFaq
    );


    QuestionEntity savedQuestion = questionRepository.save(q);



    AnswerEntity a = new AnswerEntity(
        answer,
        savedQuestion,
        (AdminEntity) null
    );

    answerRepository.save(a);

    return toDto(savedQuestion);
  }



  /**
   * 카테고리별 FAQ 조회
   */
  @Override
  @Transactional(readOnly = true)
  public Page<FaqDTO> getFaqsByCategory(Category category, Pageable pageable) {

    Page<QuestionEntity> entities;

    if (category == null) {

      entities = questionRepository.findByIsFaqTrue(pageable);
    } else {

      entities = questionRepository.findByCategoryAndIsFaqTrue(category, pageable);
    }

    return entities.map(this::toDto);
  }

  /**
   * 제목 기반 FAQ 검색
   */
  @Override
  @Transactional(readOnly = true)
  public Page<FaqDTO> searchFaq(String keyword, Pageable pageable) {
    return questionRepository
        .findByTitleContainingIgnoreCaseAndIsFaqTrue(keyword, pageable)
        .map(this::toDto);
  }

  /**
   * FAQ 삭제
   */
  @Override
  public void deleteFaq(Long faqId) {
    // FAQ인 경우만 삭제하도록 검증 로직을 추가하면 더 안전합니다.
    QuestionEntity q = questionRepository.findById(faqId)
        .orElseThrow(() -> new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "관련 FAQ를 찾을 수 없습니다."));

    if (!q.isFaq()) {
      throw new BaseException(ErrorCode.INVALID_REQUEST, "FAQ 데이터만 삭제할 수 있습니다.");
    }

    questionRepository.delete(q);
  }


  private FaqDTO toDto(QuestionEntity q) {
    String answerText = q.getAnswers().isEmpty() ? null : q.getAnswers().get(0).getAnswer();

    return FaqDTO.builder()
        .id(q.getId())
        .question(q.getTitle())
        .answer(answerText)
        .category(q.getCategory())
        .build();
  }
}
