package com.ssafy.bjbj.api.readinggroup.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString(of = {"readingGroupBoardCommentSeq", "memberNickname", "createdDate", "modifiedDate"})
@NoArgsConstructor
@Getter
public class ResReadingGroupBoardCommentDto {

    private Long readingGroupBoardCommentSeq;

    private String memberNickname;

    private String content;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    @Builder
    public ResReadingGroupBoardCommentDto(Long readingGroupBoardCommentSeq, String memberNickname, String content, LocalDate createdDate, LocalDate modifiedDate) {
        this.readingGroupBoardCommentSeq = readingGroupBoardCommentSeq;
        this.memberNickname = memberNickname;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
