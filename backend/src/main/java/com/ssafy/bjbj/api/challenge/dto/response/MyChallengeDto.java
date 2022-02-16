package com.ssafy.bjbj.api.challenge.dto.response;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public MyChallengeDto(Long challengeSeq, String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.challengeSeq = challengeSeq;
        this.title = title;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
    }

}
