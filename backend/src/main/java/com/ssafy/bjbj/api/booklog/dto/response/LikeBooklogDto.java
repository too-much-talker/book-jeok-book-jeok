package com.ssafy.bjbj.api.booklog.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = { "booklogSeq", "title", "createdDate", "isOpen", "imgUrl" })
@Getter
public class LikeBooklogDto {

    private Long booklogSeq;

    private String title;

    private LocalDate createdDate;

    private boolean isOpen;

    private String imgUrl;

    @QueryProjection
    public LikeBooklogDto(Long booklogSeq, String title, LocalDateTime createdDate, boolean isOpen, String imgUrl) {
        this.booklogSeq = booklogSeq;
        this.title = title;
        this.createdDate = createdDate.toLocalDate();
        this.isOpen = isOpen;
        this.imgUrl = imgUrl;
    }

}
