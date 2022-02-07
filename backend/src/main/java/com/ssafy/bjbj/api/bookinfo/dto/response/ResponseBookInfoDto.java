package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(of = {"seq", "isbn", "title", "author", "description", "price", "smallImgUrl", "largeImgUrl", "categoryId", "categoryName", "publisher", "publicationDate", "starRating"})
@Getter
public class ResponseBookInfoDto {

    private Long seq;

    private String isbn;

    private String title;

    private String author;

    private String description;

    private Integer price;

    private String smallImgUrl;

    private String largeImgUrl;

    private Integer categoryId;

    private String categoryName;

    private String publisher;

    private LocalDateTime publicationDate;

    private Double starRating;

    @QueryProjection
    public ResponseBookInfoDto(Long seq, String isbn, String title, String author, String description, Integer price, String smallImgUrl, String largeImgUrl, Integer categoryId, String categoryName, String publisher, LocalDateTime publicationDate, Double starRating) {
        this.seq = seq;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.smallImgUrl = smallImgUrl;
        this.largeImgUrl = largeImgUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.starRating = starRating;
    }
}
