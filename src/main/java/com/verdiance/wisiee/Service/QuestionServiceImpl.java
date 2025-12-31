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

    if (request == null || user == null) {
      throw new ResourceNotFoundException("잘못된 요청입니다.");
    }

    QuestionEntity entity = new QuestionEntity(
        user,
        request.getTitle(),
        request.getContent(),
        request.getCategory()
    );

    QuestionEntity saved = questionRepository.save(entity);
    return QuestionDTO.from(saved);
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

    entity.update(request);
    return QuestionDTO.from(entity);
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

    questionRepository.delete(entity);
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

    QuestionEntity q = questionRepository.findById(questionId)
        .orElseThrow(() ->
            new ResourceNotFoundException("Question 찾을 수 없습니다. ID=" + questionId)
        );

    if (q.isClosed()) return;

    q.close();
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


  @Override
  @Transactional(readOnly = true)
  public Page<QuestionDTO> getQuestionsByUser(UserEntity user, int page) {

    Pageable pageable = PageRequest.of(
        page,
        5,
        Sort.by(Sort.Direction.DESC, "createdAt")
    );

    return questionRepository
        .findByUser(user, pageable)
        .map(entity -> QuestionDTO.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .category(entity.getCategory())
            .closed(entity.isClosed())
            .createdAt(entity.getCreatedAt())
            .build()
        );
  }



}
