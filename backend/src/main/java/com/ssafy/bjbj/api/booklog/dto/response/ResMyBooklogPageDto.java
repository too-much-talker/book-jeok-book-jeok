package com.ssafy.bjbj.api.booklog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResMyBooklogPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<MyBooklogDto> myBooklogDtos;

    @Builder
    public ResMyBooklogPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<MyBooklogDto> myBooklogDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.myBooklogDtos = myBooklogDtos;
    }

}
