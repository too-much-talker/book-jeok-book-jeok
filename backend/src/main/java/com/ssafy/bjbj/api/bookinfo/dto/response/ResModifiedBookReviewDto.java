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

}