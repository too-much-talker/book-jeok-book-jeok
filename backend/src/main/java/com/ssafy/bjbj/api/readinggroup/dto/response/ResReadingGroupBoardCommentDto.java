package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = {"readingGroupBoardCommentSeq", "memberNickname", "createdDate", "modifiedDate"})
@NoArgsConstructor
@Getter
public class ResReadingGroupBoardCommentDto {

    private Long readingGroupBoardCommentSeq;

    private String memberNickname;

    private String content;

    private LocalDate createdDate;

    private LocalDate modifiedDate;

    @QueryProjection
    @Builder
    public ResReadingGroupBoardCommentDto(Long readingGroupBoardCommentSeq, String memberNickname, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.readingGroupBoardCommentSeq = readingGroupBoardCommentSeq;
        this.memberNickname = memberNickname;
        this.content = content;
        this.createdDate = createdDate.toLocalDate();
        this.modifiedDate = modifiedDate.toLocalDate();
    }
}
