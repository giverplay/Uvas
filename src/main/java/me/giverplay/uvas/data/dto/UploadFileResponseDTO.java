package me.giverplay.uvas.data.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long length;

  public UploadFileResponseDTO(String fileName, String fileDownloadUri, String fileType, long length) {
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.length = length;
  }

  public UploadFileResponseDTO(){}

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileDownloadUri() {
    return fileDownloadUri;
  }

  public void setFileDownloadUri(String fileDownloadUri) {
    this.fileDownloadUri = fileDownloadUri;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public long getLength() {
    return length;
  }

  public void setLength(long length) {
    this.length = length;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UploadFileResponseDTO that = (UploadFileResponseDTO) o;
    return length == that.length && Objects.equals(fileName, that.fileName) && Objects.equals(fileDownloadUri, that.fileDownloadUri) && Objects.equals(fileType, that.fileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, fileDownloadUri, fileType, length);
  }
}
