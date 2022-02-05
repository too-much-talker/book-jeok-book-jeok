package com.ssafy.bjbj.api.booklog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResLikeBooklogPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<LikeBooklogDto> likeBooklogDtos;

    @Builder
    public ResLikeBooklogPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<LikeBooklogDto> likeBooklogDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.likeBooklogDtos = likeBooklogDtos;
    }

}
