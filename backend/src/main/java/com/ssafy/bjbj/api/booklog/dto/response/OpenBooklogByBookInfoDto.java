package com.ssafy.bjbj.api.booklog.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = { "booklogSeq", "title", "content", "createdDate", "isOpen", "imgUrl" })
@Getter
public class OpenBooklogByBookInfoDto {

    private Long booklogSeq;

    private String title;

    private String content;

    private LocalDate createdDate;

    private boolean isOpen;

    private String imgUrl;

    @QueryProjection
    public OpenBooklogByBookInfoDto(Long booklogSeq, String title, String content, LocalDateTime createdDate, boolean isOpen, String imgUrl) {
        this.booklogSeq = booklogSeq;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate.toLocalDate();
        this.isOpen = isOpen;
        this.imgUrl = imgUrl;
    }


}
