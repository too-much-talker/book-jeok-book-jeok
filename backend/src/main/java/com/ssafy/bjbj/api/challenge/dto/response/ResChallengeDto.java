package com.ssafy.bjbj.api.challenge.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = {"challengeSeq", "title", "content", "startDate", "endDate", "deadline", "reward", "maxMember"})
@Getter
public class ResChallengeDto {

    private Long challengeSeq;

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate deadline;

    private Integer reward;

    private Integer maxMember;

    @QueryProjection
    public ResChallengeDto(Long challengeSeq, String title, String content, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime deadline, Integer reward, Integer maxMember) {
        this.challengeSeq = challengeSeq;
        this.title = title;
        this.content = content;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.deadline = deadline.toLocalDate();
        this.reward = reward;
        this.maxMember = maxMember;
    }

}
