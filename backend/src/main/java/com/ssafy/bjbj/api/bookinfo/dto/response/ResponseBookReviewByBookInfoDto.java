package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(of = {"memberNickname", "startRating", "summary", "createdDate"})
@NoArgsConstructor
@Getter
public class ResponseBookReviewByBookInfoDto {

    private Long bookReviewSeq;

    private Long bookInfoSeq;

    private Long memberSeq;

    private String memberNickname;

    private Integer starRating;

    private String summary;

    private LocalDateTime createdDate;

    @QueryProjection
    @Builder
    public ResponseBookReviewByBookInfoDto(Long bookReviewSeq, Long bookInfoSeq, Long memberSeq, String memberNickname, Integer starRating, String summary, LocalDateTime createdDate) {
        this.bookReviewSeq = bookReviewSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.memberNickname = memberNickname;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate;
    }
}
