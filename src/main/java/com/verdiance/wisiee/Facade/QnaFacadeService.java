package com.verdiance.wisiee.Facade;

import com.verdiance.wisiee.DTO.File.FileRequestDTO;
import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.File.FileUploadFailedException;
import com.verdiance.wisiee.Service.Interface.AnswerServie;
import com.verdiance.wisiee.Service.Interface.FileService;
import com.verdiance.wisiee.Service.Interface.QuestionService;
import com.verdiance.wisiee.Service.Interface.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaFacadeService {

  private final QuestionService questionService;
  private final AnswerServie answerService;
  private final UserService userService;
  private final FileService fileService;

  private UserEntity currentUser() {
    return userService.getUser();
  }


  // 1. 문의 등록
  public QuestionDTO createQuestion(QuestionRequestDTO dto, List<MultipartFile> files) {

    UserEntity user = currentUser();

    // 1) 문의글 생성
    QuestionDTO created = questionService.postQna(dto, user);
    Long questionId = created.getId();

    // 2) 파일 업로드 (최대 3개)
    uploadQuestionFiles(questionId, files);

    // 3) 파일 포함된 DTO 반환
    created.setFiles(fileService.getFileList(questionId));
    return created;
  }




  // 2. 문의 수정

  public QuestionDTO updateQuestion(QuestionRequestDTO dto, List<MultipartFile> files) {

    UserEntity user = currentUser();

    // 1) 본문 수정
    QuestionDTO updated = questionService.putQna(dto, user);
    Long questionId = updated.getId();

    // 2) 기존 파일 삭제
    fileService.getFileList(questionId).forEach(f ->
        fileService.deleteFile(f.getId())
    );

    // 3) 신규 파일 업로드
    uploadQuestionFiles(questionId, files);

    updated.setFiles(fileService.getFileList(questionId));
    return updated;
  }



  // 3. 문의 삭제
  public void deleteQuestion(Long questionId) {

    UserEntity user = currentUser();

    // 파일 삭제
    fileService.getFileList(questionId).forEach(f ->
        fileService.deleteFile(f.getId())
    );

    // 문의 삭제
    questionService.deleteQna(
        QuestionRequestDTO.builder().id(questionId).build(),
        user
    );
  }


  // 4. 답변 등록
  public AnswerDTO registerAnswer(AnswerRequestDTO dto, boolean fromAdmin) {
    UserEntity sender = currentUser();
    return answerService.registerAnswer(dto, sender, fromAdmin);
  }


  // 5. 답변 수정
  public AnswerDTO updateAnswer(Long answerId, AnswerRequestDTO dto) {
    UserEntity sender = currentUser();
    return answerService.updateAnswer(answerId, dto, sender);
  }



  // 6. 질문자가 문의 종료
  public void closeQuestionByUser(Long questionId) {
    UserEntity questioner = currentUser();
    answerService.closeAnswerByQuestioner(questionId, questioner);
  }

  // 7. 관리자가 문의 종료
  public void closeQuestionByAdmin(Long questionId) {
    UserEntity admin = currentUser();
    questionService.closeByAdmin(questionId, admin);
  }


  // 7. 질문 상세 조회 (제목으로만 검색)
  public QuestionDTO getQuestionDetail(Long questionId) {

    QuestionDTO question = questionService.getQuestion(questionId);

    question.setFiles(fileService.getFileList(questionId));
    question.setAnswers(answerService.getAnswers(questionId));

    return question;
  }



  // 8. 문의게시판 파일 업로드 헬퍼
  private void uploadQuestionFiles(Long questionId, List<MultipartFile> files) {

    if (files == null || files.isEmpty()) return;

    if (files.size() > 3) {
      throw new FileUploadFailedException("문의글은 최대 3개의 파일만 업로드 가능합니다.");
    }

    for (MultipartFile file : files) {
      try {
        FileRequestDTO meta = FileRequestDTO.builder()
            .name(file.getOriginalFilename())
            .description("문의 첨부파일")
            .build();

        fileService.createFile(
            questionId,
            meta,
            file.getBytes(),
            file.getContentType()
        );

      } catch (Exception e) {
        log.error("QnA 파일 업로드 실패: {}", e.getMessage(), e);
        throw new FileUploadFailedException("파일 업로드 실패: " + e.getMessage());
      }
    }
  }

  // 페이지네이션
  public Page<QuestionDTO> searchByTitle(String title, int page) {
    return questionService.searchByTitle(title, page);
  }


  public Page<QuestionDTO> getMyQuestions(int page) {
    return questionService.getQuestionsByUser(currentUser(), page);
  }




}
