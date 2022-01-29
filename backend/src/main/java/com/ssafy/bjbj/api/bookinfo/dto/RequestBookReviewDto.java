package com.ssafy.bjbj.api.bookinfo.dto;

import lombok.*;

@ToString(of = {"bookInfo", "member", "startRating", "summary"})
@NoArgsConstructor
@Getter
public class RequestBookReviewDto {

    private Long bookInfoSeq;

    private Long memberSeq;

    private Integer starRating;

    private String summary;

    @Builder
    public RequestBookReviewDto(Long bookInfoSeq, Long memberSeq, Integer starRating, String summary) {
        this.bookInfoSeq = bookInfoSeq;
        this.memberSeq = memberSeq;
        this.starRating = starRating;
        this.summary = summary;
    }


}
