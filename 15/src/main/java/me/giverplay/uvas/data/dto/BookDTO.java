package me.giverplay.uvas.data.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private long id;
  private double price;

  private String title;
  private String author;
  private Date launchDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getLaunchDate() {
    return launchDate;
  }

  public void setLaunchDate(Date launchDate) {
    this.launchDate = launchDate;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    BookDTO bookDTO = (BookDTO) object;
    return id == bookDTO.id && Double.compare(price, bookDTO.price) == 0 && Objects.equals(title, bookDTO.title) && Objects.equals(author, bookDTO.author) && Objects.equals(launchDate, bookDTO.launchDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, price, title, author, launchDate);
  }
}
