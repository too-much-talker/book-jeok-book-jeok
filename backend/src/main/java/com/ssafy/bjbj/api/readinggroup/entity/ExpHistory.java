package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_exp_history")
@Entity
public class ExpHistory extends BaseCreatedEntity {

    @Column(name = "exp_history_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer expDiff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpDiffReason expDiffReason;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

}