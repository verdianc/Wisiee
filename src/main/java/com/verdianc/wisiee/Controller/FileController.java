package com.verdianc.wisiee.Controller;


import com.verdianc.wisiee.DTO.File.FileDTO;
import com.verdianc.wisiee.DTO.File.FileRequestDTO;
import com.verdianc.wisiee.DTO.Form.FormRequestDTO;
import com.verdianc.wisiee.DTO.ResDTO;
import com.verdianc.wisiee.Service.Interface.FileService;
import com.verdianc.wisiee.facade.FileFacadeService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

  private final FileFacadeService fileFacadeService;


  @PostMapping(value = "/{formId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResDTO<FileDTO> createFile(
      @PathVariable Long formId,
      @RequestPart("file") MultipartFile file,
      @RequestPart("meta") FileRequestDTO request
  ) throws IOException {
    FileDTO fileDTO = fileFacadeService.createFile(
        formId,
        request,
        file.getBytes(),
        file.getContentType()
    );
    return new ResDTO<>(fileDTO);
  }

  @GetMapping("/{id}")
  public ResDTO<FileDTO> getFile(@PathVariable Long id) {
    return new ResDTO<>(fileFacadeService.getFile(id));
  }

  @GetMapping("/form/{formId}")
  public ResDTO<List<FileDTO>> getFileList(@PathVariable Long formId) {
    return new ResDTO<>(fileFacadeService.getFileList(formId));
  }


  @DeleteMapping("/{id}")
  public ResDTO<Void> deleteFile(@PathVariable Long id) {
    fileFacadeService.deleteFile(id);
    return new ResDTO<>(null);
  }
}
