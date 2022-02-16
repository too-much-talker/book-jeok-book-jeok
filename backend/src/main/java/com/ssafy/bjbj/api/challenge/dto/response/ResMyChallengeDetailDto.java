package com.ssafy.bjbj.api.challenge.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString(of = {"challengeSeq", "writerSeq", "title", "content", "startDate", "endDate", "deadline", "reward", "maxMember", "views", "isTodayAuth", "authRate", "participantSeqs", "participantNicknames", "authDates"})
@Getter
public class ResMyChallengeDetailDto {

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

    private boolean isTodayAuth;

    private double authRate;

    private List<Long> participantSeqs;

    private List<String> participantNicknames;

    private List<LocalDate> authDates;

    @Builder
    public ResMyChallengeDetailDto(Long challengeSeq, Long writerSeq, String title, String content, LocalDate startDate, LocalDate endDate, LocalDate deadline, Integer reward, Integer maxMember, Integer views, boolean isTodayAuth, double authRate, List<Long> participantSeqs, List<String> participantNicknames, List<LocalDate> authDates) {
        this.challengeSeq = challengeSeq;
        this.writerSeq = writerSeq;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deadline = deadline;
        this.reward = reward;
        this.maxMember = maxMember;
        this.views = views;
        this.isTodayAuth = isTodayAuth;
        this.authRate = authRate;
        this.participantSeqs = participantSeqs;
        this.participantNicknames = participantNicknames;
        this.authDates = authDates;
    }

}
