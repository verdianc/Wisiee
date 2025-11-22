package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Qna.QnaDTO;
import com.verdiance.wisiee.DTO.Qna.QnaRequestDTO;
import com.verdiance.wisiee.Entity.UserEntity;

public interface QuestionService {

  QnaDTO postQna(QnaRequestDTO qnaRequestDTO, UserEntity user);

  QnaDTO putQna(QnaRequestDTO qnaRequestDTO, UserEntity user);

  void  deleteQna(QnaRequestDTO qnaRequestDTO, UserEntity user);




}
