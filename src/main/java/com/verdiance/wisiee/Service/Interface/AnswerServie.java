package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.Entity.UserEntity;
import java.util.List;

public interface AnswerServie {


  // 답변(메시지) 등록 — 사용자/관리자 모두 가능
  AnswerDTO registerAnswer(AnswerRequestDTO dto, UserEntity sender, boolean fromAdmin);

  // 해당 문의의 전체 답변 리스트 조회
  List<AnswerDTO> getAnswers(Long questionId);

  // 특정 답변 수정
  AnswerDTO updateAnswer(Long answerId, AnswerRequestDTO dto, UserEntity sender);

  // 질문자가 문의 종료하기
  void closeAnswerByQuestioner(Long questionId, UserEntity questioner);

} 