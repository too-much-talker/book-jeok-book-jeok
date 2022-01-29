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
    
}