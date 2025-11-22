package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.Facade.QnaFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaController {

  private final QnaFacadeService qnaFacadeService;

  //1. 사용자가 작성한 게시글 목록 조회

  //2. Admin 권한 관리자의 답변 등록

  //3. 문의글 수정

  //4. 문의글 삭제

  //5. 제목으로 검색

  //6. 페이지네이션 ?

  //7. Admin 권한 관리자의 문의 종료

  //8. 문의글 전체 리스트 조회

}
