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
    @GeneratedValue
    @Id
    private Long id;

    private Long entityId;

    private Type type;

    private LocalDateTime time;

    private boolean isDeleted;

}
