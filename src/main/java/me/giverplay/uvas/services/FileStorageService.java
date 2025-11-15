package me.giverplay.uvas.services;

import me.giverplay.uvas.config.FileStorageConfig;
import me.giverplay.uvas.controllers.FileController;
import me.giverplay.uvas.exception.exceptions.FileNotFoundException;
import me.giverplay.uvas.exception.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class.getName());

  private final Path fileStoragePath;

  @Autowired
  public FileStorageService(FileStorageConfig config) {
    Path path = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();

    try {
      LOGGER.info("Creating storage directories");
      Files.createDirectories(path);
    } catch (IOException e) {
      LOGGER.error("Failed to create storage directories");
      throw new FileStorageException("Failed to create storage directories", e);
    }

    this.fileStoragePath = path;
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      if (fileName.contains("..")) {
        LOGGER.warn("File name contains invalid an invalid char sequence (..)");
        throw new FileStorageException("File name contains invalid an invalid char sequence (..)");
      }

      LOGGER.info("Saving file");
      Path targetFile = this.fileStoragePath.resolve(fileName);
      Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (IOException e) {
      LOGGER.error("Failed to store file {}", fileName);
      throw new FileStorageException("Failed to store file " + fileName, e);
    }
  }

  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = this.fileStoragePath.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        return resource;
      }

      throw new java.io.FileNotFoundException(filePath.toString());
    } catch (Exception e) {
      LOGGER.error("File not found {}", fileName);
      throw new FileNotFoundException("File not found " + fileName, e);
    }
  }
}
