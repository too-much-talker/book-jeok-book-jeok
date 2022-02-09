package com.ssafy.bjbj.api.booklog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage", "openBooklogByBookInfoDtos" })
@Getter
public class ResOpenBooklogPageByBookInfoDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<OpenBooklogByBookInfoDto> openBooklogByBookInfoDtos;

    @Builder
    public ResOpenBooklogPageByBookInfoDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<OpenBooklogByBookInfoDto> openBooklogByBookInfoDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.openBooklogByBookInfoDtos = openBooklogByBookInfoDtos;
    }

}
