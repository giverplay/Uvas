package me.giverplay.uvas.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.giverplay.uvas.controllers.docs.FileControllerDocs;
import me.giverplay.uvas.data.dto.UploadFileResponseDTO;
import me.giverplay.uvas.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
public class FileController implements FileControllerDocs {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class.getName());

  @Autowired
  private FileStorageService service;

  @PostMapping("/upload")
  @Override
  public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
    String fileName = service.storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder
      .fromCurrentContextPath()
      .path("/api/v1/file/download/")
      .path(fileName)
      .toUriString();

    return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
  }

  @PostMapping("/upload/bulk")
  @Override
  public List<UploadFileResponseDTO> uploadFiles(@RequestParam("file") MultipartFile[] files) {
    return Arrays.stream(files).map(this::uploadFile).toList();
  }

  @GetMapping("/download/{file:.+}")
  @Override
  public ResponseEntity<Resource> downloadFile(@PathVariable("file") String fileName, HttpServletRequest request) {
    Resource resource = service.loadFileAsResource(fileName);
    String contentType = null;

    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (Exception e) {
      LOGGER.error("Could not determine file type of {}", fileName);
    }


    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(contentType))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment=\"" + resource.getFilename() + "\"")
      .body(resource);
  }
}
