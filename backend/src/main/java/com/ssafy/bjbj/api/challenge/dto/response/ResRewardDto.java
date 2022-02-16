package com.ssafy.bjbj.api.challenge.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString(of = {"challengeSeq", "memberSeq", "rewards"})
@Getter
public class ResRewardDto {

    private Long challengeSeq;

    private Long memberSeq;

    private Integer rewards;

    @Builder
    public ResRewardDto(Long challengeSeq, Long memberSeq, Integer rewards) {
        this.challengeSeq = challengeSeq;
        this.memberSeq = memberSeq;
        this.rewards = rewards;
    }
}
