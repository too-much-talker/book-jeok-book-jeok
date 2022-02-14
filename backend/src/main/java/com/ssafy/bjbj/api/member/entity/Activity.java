package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.api.member.enums.ActivityType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Getter
@ToString(of = {"seq", "activityType", "time", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_activity")
@Entity
public class Activity {

    @Column(name = "activity_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private Long referSeq;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime time;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    public Activity(Long referSeq, ActivityType activityType, LocalDateTime time, Member member) {
        this.referSeq = referSeq;
        this.activityType = activityType;
        this.time = time;
        this.isDeleted = false;
        this.member = member;
    }

    public void delete() {
        this.isDeleted = true;
    }

}
