package com.ssafy.bjbj.api.challenge.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.common.enums.Status;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = {"challengeSeq", "title", "startDate", "endDate"})
@Getter
public class MyChallengeDto {

    private Long challengeSeq;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private int numOfParticipants;

    private int maxMember;

    private int reward;

    private Status status;

    private double authRate;

    @QueryProjection
    public MyChallengeDto(Long challengeSeq, String title, LocalDateTime startDate, LocalDateTime endDate, int numOfParticipants, int maxMember, int reward, Status status, double authRate) {
        this.challengeSeq = challengeSeq;
        this.title = title;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.numOfParticipants = numOfParticipants;
        this.maxMember = maxMember;
        this.reward = reward;
        this.status = status;
        this.authRate = authRate;
    }

}
