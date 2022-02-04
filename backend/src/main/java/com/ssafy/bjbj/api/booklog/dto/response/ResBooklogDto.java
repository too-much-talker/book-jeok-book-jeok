package com.ssafy.bjbj.api.booklog.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString(of = { "booklogSeq", "memberSeq", "title", "content", "summary", "starRating", "readDate", "isOpen", "views", "createdDate"})
@Getter
public class ResBooklogDto {

    private Long booklogSeq;

    private Long memberSeq;

    private String title;

    private String content;

    private String summary;

    private Integer starRating;

    private LocalDate readDate;

    private Boolean isOpen;

    private Integer views;

    private LocalDate createdDate;

    @QueryProjection
    @Builder
    public ResBooklogDto(Long booklogSeq, Long memberSeq, String title, String content, String summary, Integer starRating, LocalDate readDate, Boolean isOpen, Integer views, LocalDate createdDate) {
        this.booklogSeq = booklogSeq;
        this.memberSeq = memberSeq;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.starRating = starRating;
        this.readDate = readDate;
        this.isOpen = isOpen;
        this.views = views;
        this.createdDate = createdDate;
    }

}
