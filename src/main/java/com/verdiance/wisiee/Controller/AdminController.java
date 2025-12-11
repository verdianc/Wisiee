package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.Qna.AnswerDTO;
import com.verdiance.wisiee.DTO.Qna.AnswerRequestDTO;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.DTO.User.AdminLoginRequestDTO;
import com.verdiance.wisiee.Facade.QnaFacadeService;
import com.verdiance.wisiee.Service.Interface.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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





}
