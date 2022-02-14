package com.ssafy.bjbj.api.readinggroup.dto.response;

import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.common.enums.Day;
import com.ssafy.bjbj.common.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@ToString(of = {"readingGroupSeq", "title", "status", "readingGroupType", "days"})
@NoArgsConstructor
@Getter
public class MyReadingGroupDtoWithDays {

    private Long readingGroupSeq;

    private String title;

    private Status status;

    private ReadingGroupType readingGroupType;

    private Set<Day> days;

    @Builder
    public MyReadingGroupDtoWithDays(Long readingGroupSeq, String title, Status status, ReadingGroupType readingGroupType, Set<Day> days) {
        this.readingGroupSeq = readingGroupSeq;
        this.title = title;
        this.status = status;
        this.readingGroupType = readingGroupType;
        this.days = days;
    }

}
