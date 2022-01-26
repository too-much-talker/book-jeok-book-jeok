package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.common.entity.base.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_point_history")
@Entity
public class PointHistory extends BaseCreatedEntity {

    @Column(name = "point_history_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer pointDiff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointDiffReason pointDiffReason;

}