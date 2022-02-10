package com.ssafy.bjbj.api.readinggroup.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString(of = {"readingGroupSeq", "title", "content"})
@NoArgsConstructor
@Getter
public class ReqReadingGroupBoardDto {

    private Long readingGroupSeq;

    @NotEmpty(message = "제목을 작성해주세요.")
    private String title;

    @NotEmpty(message = "본문을 작성해주세요.")
    private String content;

    @Builder
    public ReqReadingGroupBoardDto(Long readingGroupSeq, String title, String content) {
        this.readingGroupSeq = readingGroupSeq;
        this.title = title;
        this.content = content;
    }

}
