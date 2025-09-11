package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.Service.Interface.FormService;
import com.verdianc.wisiee.facade.FormFacadeService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormController {

  private final FormFacadeService formFacadeService;

  @PostMapping(value = "/forms", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResDTO<FormDTO> createForm(
      @RequestPart("form") FormRequestDTO request,
      @RequestPart(value = "files", required = false) List<MultipartFile> files
  ) {
    return new ResDTO<>(formFacadeService.createForm(request, files));
  }






}