package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupDate;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.common.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString(of = {"readingGroupSeq", "title", "status", "readingGroupType", "readingGroupDates"})
@NoArgsConstructor
@Getter
public class MyReadingGroupDto {

    private Long readingGroupSeq;

    private String title;

    private Status status;

    private ReadingGroupType readingGroupType;

    private List<LocalDateTime> readingGroupDates;

    @QueryProjection
    public MyReadingGroupDto(Long readingGroupSeq, String title, Status status, ReadingGroupType readingGroupType, List<LocalDateTime> readingGroupDates) {
        this.readingGroupSeq = readingGroupSeq;
        this.title = title;
        this.status = status;
        this.readingGroupType = readingGroupType;
        this.readingGroupDates = readingGroupDates;
    }
}
