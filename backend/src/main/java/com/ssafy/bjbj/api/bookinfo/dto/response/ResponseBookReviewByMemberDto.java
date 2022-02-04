package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(of = {"startRating", "summary", "createdDate"})
@NoArgsConstructor
@Getter
public class ResponseBookReviewByMemberDto {

    private Long bookReviewSeq;

    private Long bookInfoSeq;

    private Long memberSeq;

    private String bookTitle;

    private String bookAuthor;

    private String memberNickname;

    private Integer starRating;

    private String summary;

    private LocalDateTime createdDate;

    @QueryProjection
    @Builder
    public ResponseBookReviewByMemberDto(Long bookReviewSeq, Long bookInfoSeq, Long memberSeq, String bookTitle, String bookAuthor, String memberNickname, Integer starRating, String summary, LocalDateTime createdDate) {
        this.bookReviewSeq = bookReviewSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.memberNickname = memberNickname;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate;
    }
}

