package com.ssafy.bjbj.api.challenge.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ToString(of = {"challengeSeq", "title", "content", "startDate", "endDate", "deadline", "reward", "maxMember", "participantSeqs", "participantNicknames"})
@Getter
public class ResChallengeDto {

    private Long challengeSeq;

    private Long writerSeq;

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate deadline;

    private Integer reward;

    private Integer maxMember;

    private Integer views;

    private List<Long> participantSeqs;

    private List<String> participantNicknames;

    @QueryProjection
    @Builder
    public ResChallengeDto(Long challengeSeq, Long writerSeq, String title, String content, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime deadline, Integer reward, Integer maxMember, Integer views, List<Long> participantSeqs, List<String> participantNicknames) {
        this.challengeSeq = challengeSeq;
        this.writerSeq = writerSeq;
        this.title = title;
        this.content = content;
        this.startDate = startDate.toLocalDate();
        this.endDate = endDate.toLocalDate();
        this.deadline = deadline.toLocalDate();
        this.reward = reward;
        this.maxMember = maxMember;
        this.views = views;
        this.participantSeqs = participantSeqs;
        this.participantNicknames = participantNicknames;
    }

}
