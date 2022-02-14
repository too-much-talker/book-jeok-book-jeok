package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.entity.Activity;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.enums.ActivityType;
import com.ssafy.bjbj.api.member.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public Activity createNewActivity(Long referSeq, Member member, ActivityType activityType, LocalDateTime time) {
        return activityRepository.save(Activity.builder()
                .referSeq(referSeq)
                .member(member)
                .activityType(activityType)
                .time(time)
                .build());
    }

}
