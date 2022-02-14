package com.ssafy.bjbj.api.readinggroup.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "totalCnt", "currentPage", "totalPage" })
@Getter
public class ResMyReadingGroupPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<MyReadingGroupDtoWithDays> myReadingGroupDtos;

    @Builder
    public ResMyReadingGroupPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<MyReadingGroupDtoWithDays> myReadingGroupDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.myReadingGroupDtos = myReadingGroupDtos;
    }

}
