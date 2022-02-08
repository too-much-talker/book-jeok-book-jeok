package com.ssafy.bjbj.api.readinggroup.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = {"totalCnt", "currentPage", "totalPage"})
@Getter
public class ResReadingGroupBoardPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<ResReadingGroupArticleDto> resReadingGroupArticleDtos;

    @Builder
    public ResReadingGroupBoardPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<ResReadingGroupArticleDto> resReadingGroupArticleDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.resReadingGroupArticleDtos = resReadingGroupArticleDtos;
    }
}
