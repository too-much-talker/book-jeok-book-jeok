package com.ssafy.bjbj.api.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@ToString(of = {"date", "count"})
@Getter
public class ActivityCountDto {

    private String date;

    private int count;

    @QueryProjection
    public ActivityCountDto(String date, int count) {
        this.date = date;
        this.count = count;
    }

}
