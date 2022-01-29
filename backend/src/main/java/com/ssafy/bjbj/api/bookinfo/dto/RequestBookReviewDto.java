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
}
