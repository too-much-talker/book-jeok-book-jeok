package com.ssafy.bjbj.api.booklog.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = { "booklogSeq", "nickname", "title", "content", "likes", "createdDate", "imgUrl" })
@Getter
public class SearchBooklogDto {

    private Long booklogSeq;

    private String nickname;

    private String title;

    private String content;

    private Integer likes;

    private LocalDate createdDate;

    private String imgUrl;

    @QueryProjection
    public SearchBooklogDto(Long booklogSeq, String nickname, String title, String content, Integer likes, LocalDateTime createdDate, String imgUrl) {
        this.booklogSeq = booklogSeq;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.createdDate = createdDate.toLocalDate();
        this.imgUrl = imgUrl;
    }

}
