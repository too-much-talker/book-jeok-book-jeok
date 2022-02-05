package com.ssafy.bjbj.api.readinggroup.dto.request;

import lombok.*;

@ToString(of = {"memberSeq","readingGroupSeq", "title", "content"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReqReadingGroupBoardDto {

    private Long memberSeq;

    private Long readingGroupSeq;

    private String title;

    private String content;
}
