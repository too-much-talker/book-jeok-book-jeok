package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.readinggroup.entity.ExpDiffReason;
import com.ssafy.bjbj.common.entity.base.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "expDiff", "expDiffReason"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_exp_history")
@Entity
public class ExpHistory extends BaseCreatedEntity {

    @Column(name = "exp_history_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer expDiff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpDiffReason expDiffReason;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

}
