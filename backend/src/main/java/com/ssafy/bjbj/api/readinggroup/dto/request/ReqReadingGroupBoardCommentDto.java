package com.ssafy.bjbj.api.readinggroup.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"readingGroupArticleSeq", "content"})
@NoArgsConstructor
@Getter
public class ReqReadingGroupBoardCommentDto {

    private Long readingGroupArticleSeq;

    private String content;

    @Builder
    public ReqReadingGroupBoardCommentDto(Long readingGroupArticleSeq, String content) {
        this.readingGroupArticleSeq = readingGroupArticleSeq;
        this.content = content;
    }
}
