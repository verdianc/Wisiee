package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.Entity.AnswerEntity;
import com.verdiance.wisiee.Entity.QuestionEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Common.*;
import com.verdiance.wisiee.Repository.AnswerRepository;
import com.verdiance.wisiee.Repository.QuestionRepository;
import com.verdiance.wisiee.Service.Interface.AnswerServie;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerServie {

  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;

  @Override
  @Transactional
  public AnswerDTO registerAnswer(AnswerRequestDTO dto, UserEntity sender, boolean fromAdmin) {

    QuestionEntity q = questionRepository.findById(dto.getQuestionId())
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + dto.getQuestionId())
        );

    if (q.isClosed()) {
      throw new ResourceAccessDeniedException("이미 종료된 문의에는 답변을 남길 수 없습니다.");
    }

    try {
      AnswerEntity answer = new AnswerEntity(
          dto.getAnswer(),
          q,
          sender,
          fromAdmin
      );

      AnswerEntity saved = answerRepository.save(answer);
      return AnswerDTO.from(saved);

    } catch (Exception e) {
      log.error("답변 생성 실패: {}", e.getMessage(), e);
      throw new ResourceCreateFailedException("Answer 생성 실패: " + e.getMessage());
    }
  }


  @Override
  public List<AnswerDTO> getAnswers(Long questionId) {

    QuestionEntity q = questionRepository.findById(questionId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + questionId)
        );

    return q.getAnswers().stream()
        .sorted(Comparator.comparing(AnswerEntity::getCreatedAt))
        .map(AnswerDTO::from)
        .toList();
  }


  @Override
  @Transactional
  public AnswerDTO updateAnswer(Long answerId, AnswerRequestDTO dto, UserEntity sender) {

    AnswerEntity ans = answerRepository.findById(answerId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Answer 찾을 수 없습니다. ID=" + answerId)
        );

    if (ans.getQuestion().isClosed()) {
      throw new ResourceAccessDeniedException("이미 종료된 문의는 수정할 수 없습니다.");
    }

    // 권한 체크 (관리자만 수정 가능)
    if (!ans.isFromAdmin()) {
      throw new ResourceAccessDeniedException("사용자가 남긴 메시지는 수정할 수 없습니다.");
    }

    try {
      ans.update(dto.getAnswer());
      return AnswerDTO.from(ans);

    } catch (Exception e) {
      log.error("답변 수정 실패: {}", e.getMessage(), e);
      throw new ResourceUpdateFailedException("Answer 수정 실패: " + e.getMessage());
    }
  }


  @Override
  @Transactional
  public void closeAnswerByQuestioner(Long questionId, UserEntity questioner) {

    QuestionEntity q = questionRepository.findById(questionId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + questionId)
        );

    if (!q.getUser().getUserId().equals(questioner.getUserId())) {
      throw new ResourceAccessDeniedException("해당 문의를 종료할 권한이 없습니다.");
    }

    try {
      q.close();

    } catch (Exception e) {
      throw new ResourceUpdateFailedException("문의 종료 실패: " + e.getMessage());
    }
  }
}



