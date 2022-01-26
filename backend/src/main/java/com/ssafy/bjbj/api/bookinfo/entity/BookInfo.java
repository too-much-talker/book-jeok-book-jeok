package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(of = {"seq", "title", "vol", "seriesTitle", "seriesNo", "author", "eaIsbn", "eaAddCode", "setIsbn", "setAddCode", "setExpression", "publisher", "page", "publishDate", "subject", "titleUrl", "bookIntroductionUrl", "bookSummaryUrl", "inputDate", "updateDate", "starRating", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_info")
@Entity
public class BookInfo extends BaseLastModifiedEntity {

    @Column(name = "book_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer vol;

    @Column(nullable = false)
    private String seriesTitle;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer seriesNo;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String eaIsbn;

    @Column(nullable = false)
    private String eaAddCode;

    @Column(nullable = false)
    private String setIsbn;

    @Column(nullable = false)
    private String setAddCode;

    @Column(nullable = false)
    private boolean setExpression;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer page;

    @Column(nullable = false)
    private LocalDateTime publishDate;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String titleUrl;

    @Column(nullable = false)
    private String bookIntroductionUrl;

    @Column(nullable = false)
    private String bookSummaryUrl;

    @Column(nullable = false)
    private LocalDateTime inputDate;

    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = false, columnDefinition = "DOUBLE UNSIGNED")
    private Double starRating;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "bookInfo")
    private List<Booklog> booklogs = new ArrayList<>();

    // 나에 대해 리뷰를 쓴 멤버들
    @JoinColumn(name = "book_info_seq")
    @OneToMany
    private List<BookReview> bookReviews = new ArrayList<>();

}
