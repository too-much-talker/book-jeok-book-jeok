package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge_auth")
@Entity
public class ChallengeAuth {

    @Column(name = "challenge_auth_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(nullable = false)
    @ManyToOne(fetch = LAZY)
    private Challenge challenge;
}
