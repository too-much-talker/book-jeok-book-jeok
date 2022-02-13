package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString(of = { "readingGroupSeq", "title", "writerSeq", "readingGroupType", "deadline", "startDate", "endDate", "participantSeqs" })
@Getter
public class ReadingGroupMiniDto {

    private Long readingGroupSeq;

    private String title;

    private Long writerSeq;

    private ReadingGroupType readingGroupType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deadline;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    private List<Long> participantSeqs;

}
