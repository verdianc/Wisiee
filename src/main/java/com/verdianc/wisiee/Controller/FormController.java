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
  public ResDTO createForm(@RequestBody FormRequestDTO request) {
    return formFacadeService.createForm(request);
  }


  @GetMapping("/{id}")
  public ResDTO getForm(@PathVariable Long id) {
    return formFacadeService.getForm(id);
  }

  @GetMapping
  public ResDTO getFormList() {
    return formFacadeService.getFormList();
  }

  @PutMapping("/{id}")
  public ResDTO updateForm(@PathVariable Long id, @RequestBody FormRequestDTO request) {
    return formFacadeService.updateForm(id, request);
  }

  @DeleteMapping("/{id}")
  public ResDTO deleteForm(@PathVariable Long id) {
    return formFacadeService.deleteForm(id);
  }

}