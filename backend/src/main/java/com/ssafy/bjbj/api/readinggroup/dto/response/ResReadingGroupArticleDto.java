package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = {"memberSeq","readingGroupSeq", "readingGroupBoardSeq", "title", "content", "nickname", "createDate", "views"})
@Getter
public class ResReadingGroupArticleDto {

    private Long memberSeq;

    private Long readingGroupSeq;

    private Long readingGroupBoardSeq;

    private String title;

    private String content;

    private String nickname;

    private LocalDate createDate;

    private Integer views;

    @Builder
    @QueryProjection
    public ResReadingGroupArticleDto(Long memberSeq, Long readingGroupSeq, Long readingGroupBoardSeq, String title, String content, String nickname, LocalDateTime createDate, Integer views) {
        this.memberSeq = memberSeq;
        this.readingGroupSeq = readingGroupSeq;
        this.readingGroupBoardSeq = readingGroupBoardSeq;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createDate = createDate.toLocalDate();
        this.views = views;
    }
}
