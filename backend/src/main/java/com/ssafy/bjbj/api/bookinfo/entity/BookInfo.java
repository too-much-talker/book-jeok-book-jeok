package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(of = {"seq", "isbn", "title", "author", "description", "price", "smallImgUrl", "largeImgUrl", "categoryId", "categoryName", "publisher", "publicationDate", "starRating", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_info")
@Entity
public class BookInfo extends BaseLastModifiedEntity {

    @Column(name = "book_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer price;

    @Column(nullable = false)
    private String smallImgUrl;

    @Column(nullable = false)
    private String largeImgUrl;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "bookInfo")
    private List<Booklog> booklogs = new ArrayList<>();

    // 나에 대해 리뷰를 쓴 멤버들
    @OneToMany(mappedBy = "bookInfo")
    private List<BookReview> bookReviews = new ArrayList<>();

    @Builder
    public BookInfo(Long seq, String isbn, String title, String author, String description, Integer price, String smallImgUrl, String largeImgUrl, Integer categoryId, String categoryName, String publisher, LocalDateTime publicationDate) {
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
    }
}
