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

  @PostMapping
  public ResDTO<FormDTO> getForm(@RequestBody FormRequestDTO request) {
    return new ResDTO<>(formFacadeService.createForm(request));
  }



  @GetMapping("/{id}")
  public ResDTO<FormDTO> getForm(@PathVariable Long id) {
    return new ResDTO<>(formFacadeService.getForm(id));
  }

  @GetMapping
  public ResDTO<List<FormDTO>> getFormList() {
    return new ResDTO<>(formFacadeService.getFormList());
  }

  @DeleteMapping("/{id}")
  public ResDTO<Void> deleteForm(@PathVariable Long id) {
    formFacadeService.deleteForm(id);
    return new ResDTO<>(null);
  }


  @PostMapping("/{Id}")
  public ResDTO<FileDTO> addFileToForm(@PathVariable Long formId,
      @RequestPart("file") MultipartFile file,
      @RequestPart("meta") FileRequestDTO fileRequest) throws IOException {
    FileDTO result = formFacadeService.addFileToForm(
        formId,
        fileRequest,
        file.getBytes(),
        file.getContentType()
    );
    return new ResDTO<>(result);
  }

  @GetMapping("/files/list/{id}")
  public ResDTO<List<FileDTO>> getFilesOfForm(@PathVariable Long formId) {
    return new ResDTO<>(formFacadeService.getFilesOfForm(formId));
  }

  @GetMapping("/files/{fileId}")
  public ResDTO<FileDTO> getFile(@PathVariable Long fileId) {
    return new ResDTO<>(formFacadeService.getFile(fileId));
  }

  @DeleteMapping("/files/{fileId}")
  public ResDTO<Void> deleteFile(@PathVariable Long fileId) {
    formFacadeService.deleteFile(fileId);
    return new ResDTO<>((Void) null);
  }


}