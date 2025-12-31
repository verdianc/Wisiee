package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Qna.QuestionDTO;
import com.verdiance.wisiee.DTO.Qna.QuestionRequestDTO;
import com.verdiance.wisiee.Entity.UserEntity;
import org.springframework.data.domain.Page;

public interface QuestionService {

  QuestionDTO postQna(QuestionRequestDTO questionRequestDTO, UserEntity user);

  QuestionDTO putQna(QuestionRequestDTO questionRequestDTO, UserEntity user);

  void  deleteQna(QuestionRequestDTO questionRequestDTO, UserEntity user);

  QuestionDTO getQuestion(Long id);

  void closeByAdmin(Long questionId, UserEntity admin);

  Page<QuestionDTO> searchByTitle(String title, int page);

  Page<QuestionDTO> getQuestionsByUser(UserEntity user, int page);

  Page<QuestionDTO> getAllQuestions(int page);

}
