package com.ssafy.bjbj.api.bookinfo.dto.request;

import lombok.*;

@ToString(of = { "bookInfoSeq", "starRating", "summary" })
@NoArgsConstructor
@Getter
public class ReqBookReviewDto {

    private Long bookInfoSeq;

    private Integer starRating;

    private String summary;

    @Builder
    public ReqBookReviewDto(Long bookInfoSeq, Integer starRating, String summary) {
        this.bookInfoSeq = bookInfoSeq;
        this.starRating = starRating;
        this.summary = summary;
    }

}
