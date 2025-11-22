package com.verdiance.wisiee.Facade;

import com.verdiance.wisiee.Service.Interface.AnswerServie;
import com.verdiance.wisiee.Service.Interface.QuestionService;
import com.verdiance.wisiee.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaFacadeService {

  private final QuestionService qnaService;
  private final UserService userService;
  private final AnswerServie answerServie;



}
