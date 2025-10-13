package me.giverplay.uvas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class BookEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title", length = 250, nullable = false)
  private String title;

  @Column(name = "author", length = 180, nullable = false)
  private String author;

  @Temporal(TemporalType.DATE)
  @Column(name = "launch_date", nullable = false)
  private Date launchDate;

  @Column(precision = 65)
  private double price;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    BookEntity that = (BookEntity) object;
    return id == that.id && Double.compare(price, that.price) == 0 && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(launchDate, that.launchDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, launchDate, price);
  }
}
