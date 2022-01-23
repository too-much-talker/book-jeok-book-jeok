package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.Status;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge")
@Entity
public class Challenge extends BaseLastModifiedEntity {

    @Column(name = "challenge_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer views = 0;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer requiredAuth;

    @Column(nullable = false)
    private Integer maxMember;

    @Column(nullable = false)
    private Integer reward;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PRE;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "challenge_id")
    @OneToMany
    private List<ChallengeAuth> challengeAuths = new ArrayList<>();

    // 챌린지에 참여한 사람들
    @JoinColumn(name = "challenge_id")
    @OneToMany
    private List<ChallengeMember> challengeMembers = new ArrayList<>();

}
