package com.ssafy.bjbj.api.bookinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@ToString(of = {"startRating", "summary", "createdDate"})
@Builder
@NoArgsConstructor
@Getter
public class ResponseBookReviewDto {

    @JsonIgnore
    private Long seq;

    private Integer starRating;

    private String summary;

    private LocalDateTime createdDate;

    @QueryProjection
    public ResponseBookReviewDto(Long seq, Integer starRating, String summary, LocalDateTime createdDate) {
        this.seq = seq;
        this.starRating = starRating;
        this.summary = summary;
        this.createdDate = createdDate;
    }
}