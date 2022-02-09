package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = { "bookReviewSeq", "bookInfoSeq", "memberSeq", "memberNickname", "starRating", "summary", "createdDate" })
@NoArgsConstructor
@Getter
public class ResBookReviewByBookInfoDto {

    private Long bookReviewSeq;

    private Long bookInfoSeq;

    private Long memberSeq;

    private String memberNickname;

    private Integer starRating;

    private String summary;

    private LocalDate createdDate;

    @QueryProjection
    @Builder
    public ResBookReviewByBookInfoDto(Long bookReviewSeq, Long bookInfoSeq, Long memberSeq, String memberNickname, Integer starRating, String summary, LocalDateTime createdDate) {
        this.bookReviewSeq = bookReviewSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.memberNickname = memberNickname;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate.toLocalDate();
    }

}
