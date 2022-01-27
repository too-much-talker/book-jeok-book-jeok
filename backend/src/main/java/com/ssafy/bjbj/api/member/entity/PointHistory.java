package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.api.challenge.entity.PointDiffReason;
import com.ssafy.bjbj.common.entity.base.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "pointDiff", "pointDiffReason"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_point_history")
@Entity
public class PointHistory extends BaseCreatedEntity {

    @Column(name = "point_history_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer pointDiff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointDiffReason pointDiffReason;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

}
