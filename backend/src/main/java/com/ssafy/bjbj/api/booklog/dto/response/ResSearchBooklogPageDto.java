package com.ssafy.bjbj.api.booklog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResSearchBooklogPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<SearchBooklogDto> searchBooklogDtos;

    @Builder
    public ResSearchBooklogPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<SearchBooklogDto> searchBooklogDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.searchBooklogDtos = searchBooklogDtos;
    }

}
