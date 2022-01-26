package com.ssafy.bjbj.api.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(of = {"id", "activityType", "time", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_activity")
@Entity
public class Activity {

    @Column(name = "activity_id")
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime time;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @Builder
    public Activity(Long id, ActivityType activityType, LocalDateTime time, boolean isDeleted, Member member) {
        this.id = id;
        this.activityType = activityType;
        this.time = time;
        this.isDeleted = isDeleted;
        this.member = member;
    }

}
