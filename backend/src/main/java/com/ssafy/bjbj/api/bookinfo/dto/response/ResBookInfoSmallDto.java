package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(of = {"seq", "title", "author", "largeImgUrl", "publisher", "publicationDate", "starRating", "bookReviewCount"})
@Getter
public class ResBookInfoSmallDto {

    private Long seq;

    private String title;

    private String author;

    private String largeImgUrl;

    private String publisher;

    private LocalDateTime publicationDate;

    private Double starRating;

    private Long bookReviewCount;

    @QueryProjection
    public ResBookInfoSmallDto(Long seq, String title, String author, String largeImgUrl, String publisher, LocalDateTime publicationDate, Double starRating, Long bookReviewCount) {
        this.seq = seq;
        this.title = title;
        this.author = author;
        this.largeImgUrl = largeImgUrl;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.starRating = starRating;
        this.bookReviewCount = bookReviewCount;
    }
}
