package com.verdiance.wisiee.Controller;

import com.verdiance.wisiee.DTO.Form.FormDTO;
import com.verdiance.wisiee.DTO.Form.FormRequestDTO;
import com.verdiance.wisiee.DTO.ResDTO;
import com.verdiance.wisiee.Facade.FormFacadeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormController {

  private final FormFacadeService formFacadeService;

  // 폼생성
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResDTO<FormDTO> createForm(
      @RequestPart("form") FormRequestDTO request,
      @RequestPart(value = "files", required = false) List<MultipartFile> files
  ) {
    return new ResDTO<>(formFacadeService.createForm(request, files));
  }



  // 폼 조회
  @GetMapping("/{id}")
  public ResDTO<FormDTO> getForm(
      @PathVariable Long id,
      @RequestParam(required = false) String code
  ) {
    // code가 있으면 해당 값을, 없으면 null을 Facade로 전달
    return new ResDTO<>(formFacadeService.getForm(id, code));
  }


  // 폼 수정
  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResDTO<FormDTO> updateForm(
      @PathVariable Long id,
      @RequestPart("form") FormRequestDTO request,
      @RequestPart(value = "files", required = false) List<MultipartFile> files
  ) {
    return new ResDTO<>(formFacadeService.updateForm(id, request, files));
  }


  // 폼 삭제
  @DeleteMapping("/{id}")
  public ResDTO<Void> deleteForm(@PathVariable Long id) {
    formFacadeService.deleteForm(id);
    return new ResDTO<>(null);
  }


}