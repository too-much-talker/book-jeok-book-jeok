package com.ssafy.bjbj.api.challenge.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString(of = {"title", "deadline", "participantCount"})
@Getter
public class ChallengeMiniDto {

    private String title;

    private LocalDate deadline;

    private int participantCount;

    @QueryProjection
    public ChallengeMiniDto(String title, LocalDateTime deadline, int participantCount) {
        this.title = title;
        this.deadline = deadline.toLocalDate();
        this.participantCount = participantCount;
    }

}
