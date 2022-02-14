package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.entity.Activity;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.enums.ActivityType;

import java.time.LocalDateTime;

public interface ActivityService {

    Activity createNewActivity(Long activitySeq, Member member, ActivityType activityType, LocalDateTime time);

}
