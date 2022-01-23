package com.ssafy.bjbj.api.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
//@ToString(of = {"id", "username", "age"})
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

}
