package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(of = {"memberNickname", "startRating", "summary", "createdDate", "modifiedDate"})
@NoArgsConstructor
@Getter
public class ResModifiedBookReviewDto {

    private Long bookReviewSeq;

    private Long bookInfoSeq;

    private Long memberSeq;

    private String memberNickname;

    private Integer starRating;

    private String summary;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @QueryProjection
    @Builder
    public ResModifiedBookReviewDto(Long bookReviewSeq,Long bookInfoSeq, Long memberSeq, String memberNickname, Integer starRating, String summary, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.bookReviewSeq = bookReviewSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.memberNickname = memberNickname;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}