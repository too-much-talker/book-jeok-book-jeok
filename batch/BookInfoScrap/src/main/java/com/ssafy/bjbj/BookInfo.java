package com.ssafy.bjbj;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class BookInfo {

    private final String isbn;

    private final String title;

    private final String author;

    private final String description;

    private final Integer price;

    private final String smallImgUrl;

    private final String largeImgUrl;

    private final Integer categoryId;

    private final String categoryName;

    private final String publisher;

    private final LocalDateTime publicationDate;

    @Builder
    public BookInfo(String isbn, String title, String author, String description, Integer price, String smallImgUrl, String largeImgUrl, Integer categoryId, String categoryName, String publisher, LocalDateTime publishDate) {
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
        this.publicationDate = publishDate;
    }

}
