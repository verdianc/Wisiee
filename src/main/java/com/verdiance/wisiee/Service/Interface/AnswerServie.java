package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Qna.AnswerDTO;

public interface AnswerServie {

  AnswerDTO RegisterAnswer(AnswerDTO answerDTO);

  AnswerDTO GetAnswer(Long id);

  AnswerDTO PutAnswer(AnswerDTO answerDTO);

}
