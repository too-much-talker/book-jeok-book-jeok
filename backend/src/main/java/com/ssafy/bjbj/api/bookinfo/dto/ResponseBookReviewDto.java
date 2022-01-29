package com.ssafy.bjbj.api.bookinfo.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"bookInfo", "member", "startRating", "summary", "isDeleted"})
@NoArgsConstructor
@Getter
@Builder
public class ResponseBookReviewDto {

    private Long seq;

    private BookInfo bookInfo;

    private Member member;

    private Integer starRating;

    private String summary;

    private boolean isDeleted;

    @QueryProjection
    public ResponseBookReviewDto(Long seq, BookInfo bookInfo, Member member, Integer starRating, String summary, boolean isDeleted) {
        this.seq = seq;
        this.bookInfo = bookInfo;
        this.member = member;
        this.starRating = starRating;
        this.summary = summary;
        this.isDeleted = isDeleted;
    }
}