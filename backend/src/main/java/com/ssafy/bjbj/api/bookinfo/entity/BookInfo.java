package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_info")
@Entity
public class BookInfo extends BaseLastModifiedEntity {

    @Column(name = "book_info_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer vol;

    @Column(nullable = false)
    private String seriesTitle;

    @Column(nullable = false)
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

    @Column(nullable = false)
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

    @Column(nullable = false)
    private Double starRating;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "bookInfo")
    private List<Booklog> booklogs = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "book_info_id", referencedColumnName = "book_info_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<BookReview> bookReviews = new ArrayList<>();

}
