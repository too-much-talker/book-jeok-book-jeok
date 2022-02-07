package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@ToString(of = {"memberSeq","readingGroupSeq", "title", "content", "nickname", "createDate"})
@Getter
public class ResReadingGroupBoardDto {

    private Long memberSeq;

    private Long readingGroupSeq;

    private String title;

    private String content;

    private String nickname;

    private LocalDateTime createDate;

    @Builder
    @QueryProjection
    public ResReadingGroupBoardDto(Long memberSeq, Long readingGroupSeq, String title, String content, String nickname, LocalDateTime createDate) {
        this.memberSeq = memberSeq;
        this.readingGroupSeq = readingGroupSeq;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createDate = createDate;
    }
}
