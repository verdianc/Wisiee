package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.Faq.*;
import com.verdiance.wisiee.DTO.Qna.*;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Facade.QnaFacadeService;
import com.verdiance.wisiee.Service.Interface.AdminService;
import com.verdiance.wisiee.Service.Interface.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  private final QnaFacadeService qnaFacadeService;
  private final FaqService faqService;


  @PostMapping("/login")
  public ResDTO<Void> login(@RequestBody AdminLoginRequestDTO dto) {
    adminService.login(dto);
    return new ResDTO<>(null);
  }


  @PostMapping("/answers/{questionId}")
  public ResDTO<AnswerDTO> registerAdminAnswer(
      @PathVariable Long questionId,
      @RequestBody AnswerRequestDTO dto
  ) {
    dto.setQuestionId(questionId);
    AnswerDTO result = adminService.registerAdminAnswer(dto);
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

  // 7. 관리자가 문의 종료
  @PostMapping("/admin/close/{id}")
  public ResDTO<Void> closeQuestionByAdmin(@PathVariable Long id) {

    qnaFacadeService.closeQuestionByAdmin(id);
    return new ResDTO<>(null);
  }


  /**
   * FAQ 등록
   */
  @PostMapping("/faqs")
  public ResDTO<FaqDTO> createFaq(@RequestBody FaqRequestDTO dto) {
    FaqDTO result = faqService.createFaq(
        dto.getQuestion(),
        dto.getAnswer(),
        dto.getCategory()
    );
    return new ResDTO<>(result);
  }


  /**
   * FAQ 카테고리별 조회
   */
  @GetMapping("/faqs")
  public ResDTO<Page<FaqDTO>> getFaqsByCategory(
      @RequestParam Category category,
      @RequestParam(defaultValue = "0") int page
  ) {
    Page<FaqDTO> result = faqService.getFaqsByCategory(
        category,
        PageRequest.of(page, 10)
    );
    return new ResDTO<>(result);
  }

  /**
   * FAQ 제목 검색
   */
  @GetMapping("/faqs/search")
  public ResDTO<Page<FaqDTO>> searchFaq(
      @RequestParam String keyword,
      @RequestParam(defaultValue = "0") int page
  ) {
    Page<FaqDTO> result = faqService.searchFaq(
        keyword,
        PageRequest.of(page, 10)
    );
    return new ResDTO<>(result);
  }


  /**
   * FAQ 삭제
   */
  @DeleteMapping("/faqs/{faqId}")
  public ResDTO<Void> deleteFaq(@PathVariable Long faqId) {
    faqService.deleteFaq(faqId);
    return new ResDTO<>(null);
  }





}
