package com.ssafy.bjbj.api.booklog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResOpenBooklogPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<OpenBooklogDto> openBooklogDtos;

    @Builder
    public ResOpenBooklogPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<OpenBooklogDto> openBooklogDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.openBooklogDtos = openBooklogDtos;
    }

}
