package com.ssafy.bjbj.api.readinggroup.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResReadingGroupListPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<ReadingGroupMiniDto> readingGroupMiniDtos;

    @Builder
    public ResReadingGroupListPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<ReadingGroupMiniDto> readingGroupMiniDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.readingGroupMiniDtos = readingGroupMiniDtos;
    }

}
