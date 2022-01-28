package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.Status;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "views", "deadline", "startDate", "endDate", "requiredAuth", "maxMember", "reward", "status", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge")
@Entity
public class Challenge extends BaseLastModifiedEntity {

    @Column(name = "challenge_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer requiredAuth;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer maxMember;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer reward;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "challenge_seq")
    @OneToMany
    private List<ChallengeAuth> challengeAuths = new ArrayList<>();

    // 챌린지에 참여한 사람들
    @JoinColumn(name = "challenge_seq")
    @OneToMany
    private List<ChallengeMember> challengeMembers = new ArrayList<>();

}
