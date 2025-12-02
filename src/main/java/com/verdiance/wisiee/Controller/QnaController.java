package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.Facade.QnaFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

  private final QnaFacadeService qnaFacadeService;

  // 1. 문의 등록
  @PostMapping("/questions")
  public ResDTO<QuestionDTO> createQuestion(
      @RequestPart("data") QuestionRequestDTO dto,
      @RequestPart(value = "files", required = false)
      java.util.List<MultipartFile> files
  ) {
    QuestionDTO result = qnaFacadeService.createQuestion(dto, files);
    return new ResDTO<>(result);
  }



  // 2. 문의 수정
  @PutMapping("/questions/{questionId}")
  public ResDTO<QuestionDTO> updateQuestion(
      @PathVariable Long id,
      @RequestPart("data") QuestionRequestDTO dto,
      @RequestPart(value = "files", required = false)
      java.util.List<MultipartFile> files
  ) {
    dto.setId(id);
    QuestionDTO result = qnaFacadeService.updateQuestion(dto, files);
    return new ResDTO<>(result);
  }



  // 3. 문의 삭제
  @DeleteMapping("/questions/{questionId}")
  public ResDTO<Void> deleteQuestion(@PathVariable Long id) {

    qnaFacadeService.deleteQuestion(id);
    return new ResDTO<>(null);
  }



  // 4. 답변 등록
  @PostMapping("/answers/{answerId}")
  public ResDTO<AnswerDTO> registerAnswer(
      @PathVariable Long id,
      @RequestBody AnswerRequestDTO dto,
      @RequestParam(defaultValue = "false") boolean fromAdmin
  ) {
    dto.setQuestionId(id);
    AnswerDTO result = qnaFacadeService.registerAnswer(dto, fromAdmin);
    return new ResDTO<>(result);
  }



  // 5. 답변 수정
  @PutMapping("/answers/{answerId}")
  public ResDTO<AnswerDTO> updateAnswer(
      @PathVariable Long answerId,
      @RequestBody AnswerRequestDTO dto
  ) {
    AnswerDTO result = qnaFacadeService.updateAnswer(answerId, dto);
    return new ResDTO<>(result);
  }



  // 6. 질문자가 문의 종료
  @PostMapping("/close/{id}")
  public ResDTO<Void> closeQuestionByUser(@PathVariable Long id) {

    qnaFacadeService.closeQuestionByUser(id);
    return new ResDTO<>(null);
  }



  // 7. 관리자가 문의 종료
  @PostMapping("/admin/close/{id}")
  public ResDTO<Void> closeQuestionByAdmin(@PathVariable Long id) {

    qnaFacadeService.closeQuestionByAdmin(id);
    return new ResDTO<>(null);
  }



  // 8. 문의 상세 조회
  @GetMapping("/{id}")
  public ResDTO<QuestionDTO> getQuestionDetail(@PathVariable Long id) {

    QuestionDTO result = qnaFacadeService.getQuestionDetail(id);
    return new ResDTO<>(result);
  }



  // 9. 검색
  @GetMapping("/search")
  public ResDTO<Page<QuestionDTO>> search(
      @RequestParam String title,
      @RequestParam(defaultValue = "0") int page
  ) {
    return new ResDTO<>(qnaFacadeService.searchByTitle(title, page));
  }
}
