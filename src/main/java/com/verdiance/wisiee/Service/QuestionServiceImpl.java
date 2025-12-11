package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.Entity.QuestionEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Common.*;
import com.verdiance.wisiee.Repository.QuestionRepository;
import com.verdiance.wisiee.Service.Interface.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  @Override
  @Transactional
  public QuestionDTO postQna(QuestionRequestDTO request, UserEntity user) {

    try {
      QuestionEntity entity = new QuestionEntity(
          user,
          request.getTitle(),
          request.getContent(),
          request.getCategory()
      );

      QuestionEntity saved = questionRepository.save(entity);
      return QuestionDTO.from(saved);

    } catch (Exception e) {
      log.error("문의글 생성 실패: {}", e.getMessage(), e);
      throw new ResourceCreateFailedException("Question 생성 실패: " + e.getMessage());
    }
  }


  @Override
  @Transactional
  public QuestionDTO putQna(QuestionRequestDTO request, UserEntity user) {

    QuestionEntity entity = questionRepository.findById(request.getId())
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + request.getId())
        );

    if (!entity.getUser().getUserId().equals(user.getUserId())) {
      throw new ResourceAccessDeniedException("해당 문의를 수정할 권한이 없습니다.");
    }

    if (entity.isClosed()) {
      throw new ResourceAccessDeniedException("종료된 문의는 수정할 수 없습니다.");
    }

    try {
      entity.update(request);
      return QuestionDTO.from(entity);

    } catch (Exception e) {
      throw new ResourceUpdateFailedException("Question 수정 실패: " + e.getMessage());
    }
  }


  @Override
  @Transactional
  public void deleteQna(QuestionRequestDTO request, UserEntity user) {

    QuestionEntity entity = questionRepository.findById(request.getId())
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + request.getId())
        );

    if (!entity.getUser().getUserId().equals(user.getUserId())) {
      throw new ResourceAccessDeniedException("해당 문의를 삭제할 권한이 없습니다.");
    }

    try {
      questionRepository.delete(entity);

    } catch (Exception e) {
      throw new ResourceDeleteFailedException("Question 삭제 실패: " + e.getMessage());
    }
  }


  @Override
  public QuestionDTO getQuestion(Long id) {
    return questionRepository.findById(id)
        .map(QuestionDTO::from)
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + id)
        );
  }

  @Override
  @Transactional
  public void closeByAdmin(Long questionId, UserEntity admin) {

    // 1) 문의글 조회
    QuestionEntity q = questionRepository.findById(questionId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + questionId)
        );

    // 2) 이미 종료된 문의면 무시
    if (q.isClosed()) return;

    // 3) 관리자 권한 체크 (원하면 추가)
    // if (!admin.isAdmin()) throw new ResourceAccessDeniedException("관리자 권한이 필요합니다.");

    // 4) 종료 처리
    try {
      q.close();
    } catch (Exception e) {
      throw new ResourceUpdateFailedException("문의 종료 실패: " + e.getMessage());
    }
  }


  // 페이지네이션
  @Override
  @Transactional(readOnly = true)
  public Page<QuestionDTO> searchByTitle(String title, int page) {

    Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<QuestionEntity> result = questionRepository
        .findByTitleContainingIgnoreCase(title, pageable);

    return result.map(QuestionDTO::from);
  }

}
