package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = { "bookReviewSeq", "bookInfoSeq", "memberSeq", "bookTitle", "bookAuthor", "memberNickname", "starRating", "summary", "createdDate" })
@NoArgsConstructor
@Getter
public class ResBookReviewByMemberDto {

    private Long bookReviewSeq;

    private Long bookInfoSeq;

    private Long memberSeq;

    private String bookTitle;

    private String bookAuthor;

    private String memberNickname;

    private Integer starRating;

    private String summary;

    private LocalDate createdDate;

    @QueryProjection
    @Builder
    public ResBookReviewByMemberDto(Long bookReviewSeq, Long bookInfoSeq, Long memberSeq, String bookTitle, String bookAuthor, String memberNickname, Integer starRating, String summary, LocalDateTime createdDate) {
        this.bookReviewSeq = bookReviewSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.memberNickname = memberNickname;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate.toLocalDate();
    }

}

