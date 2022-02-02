package com.ssafy.bjbj.api.bookinfo.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"memberNickname", "startRating", "summary", "createdDate"})
@NoArgsConstructor
@Getter
public class ResponseBookReviewByBookInfoDto {

}
