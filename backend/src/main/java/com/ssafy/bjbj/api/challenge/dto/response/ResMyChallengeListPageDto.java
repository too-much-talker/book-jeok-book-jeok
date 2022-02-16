package com.ssafy.bjbj.api.challenge.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = {"totalCnt", "currentPage", "totalPage", "myChallengeDtos"})
@Getter
public class ResMyChallengeListPageDto {

    private Integer totalCnt;

    private Integer currentPage;

    private Integer totalPage;

    private List<MyChallengeDto> myChallengeDtos;

    @Builder
    public ResMyChallengeListPageDto(Integer totalCnt, Integer currentPage, Integer totalPage, List<MyChallengeDto> myChallengeDtos) {
        this.totalCnt = totalCnt;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.myChallengeDtos = myChallengeDtos;
    }

}
