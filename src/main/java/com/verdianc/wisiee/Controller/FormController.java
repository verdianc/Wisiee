package com.verdianc.wisiee.Controller;

import com.verdianc.wisiee.DTO.Form.FormDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.Service.Interface.FormService;
import com.verdianc.wisiee.facade.FormFacadeService;
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
import org.springframework.web.bind.annotation.RestController;

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


}