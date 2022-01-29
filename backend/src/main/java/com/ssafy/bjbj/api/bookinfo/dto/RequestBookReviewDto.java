package com.ssafy.bjbj.api.bookinfo.dto;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import lombok.*;

@ToString(of = {"bookInfo", "member", "startRating", "summary", "isDeleted"})
@NoArgsConstructor
@Getter
public class RequestBookReviewDto {

    private Long bookInfoSeq;

    private Long memberSeq;

    private Integer starRating;

    private String summary;

    private boolean isDeleted;
}
