package com.ssafy.bjbj.api.bookinfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = {"seq", "title", "author", "smallImgUrl", "publisher", "publicationDate", "starRating", "bookReviewCount"})
@Getter
public class ResBookListDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<ResBookInfoSmallDto> resBookInfoSmallDtos;

    @Builder
    public ResBookListDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<ResBookInfoSmallDto> resBookInfoSmallDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.resBookInfoSmallDtos = resBookInfoSmallDtos;
    }
}
