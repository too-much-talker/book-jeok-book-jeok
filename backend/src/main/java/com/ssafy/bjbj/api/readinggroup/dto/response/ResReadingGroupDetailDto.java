package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.common.enums.Day;
import com.ssafy.bjbj.common.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
public class ResReadingGroupDetailDto {

    private Long readingGroupSeq;

    private String writer;

    private String title;

    private String content;

    private Integer minLevel;

    private int maxNumOfMember;

    private int views;

    private Status status;

    private LocalDate deadline;

    private LocalDate startDate;

    private LocalDate endDate;

    private ReadingGroupType readingGroupType;

    private LocalDate createdDate;

    private List<Long> participantSeqs;

    private List<String> participants;

    private Set<Day> days;

    @Builder
    public ResReadingGroupDetailDto(Long readingGroupSeq, String writer, String title, String content, Integer minLevel, int maxNumOfMember, int views, Status status, LocalDate deadline, LocalDate startDate, LocalDate endDate, ReadingGroupType readingGroupType, LocalDate createdDate,List<Long> participantSeqs, List<String> participants, Set<Day> days) {
        this.readingGroupSeq = readingGroupSeq;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.minLevel = minLevel;
        this.maxNumOfMember = maxNumOfMember;
        this.views = views;
        this.status = status;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.readingGroupType = readingGroupType;
        this.createdDate = createdDate;
        this.participantSeqs = participantSeqs;
        this.participants = participants;
        this.days = days;
    }

}
