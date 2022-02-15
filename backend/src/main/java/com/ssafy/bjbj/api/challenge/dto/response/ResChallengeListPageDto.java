package com.ssafy.bjbj.api.challenge.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = {"totalCnt", "currentPage", "totalPage", "challengeMiniDtos"})
@Getter
public class ResChallengeListPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<ChallengeMiniDto> challengeMiniDtos;

    @Builder
    public ResChallengeListPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<ChallengeMiniDto> challengeMiniDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.challengeMiniDtos = challengeMiniDtos;
    }

}
