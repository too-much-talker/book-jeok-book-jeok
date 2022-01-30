package com.ssafy.bjbj.api.booklog.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = { "memberSeq", "booklogSeq", "title", "content", "starRating", "readDate", "isOpen"})
@NoArgsConstructor
@Getter
public class RequestBooklogDto {
}
