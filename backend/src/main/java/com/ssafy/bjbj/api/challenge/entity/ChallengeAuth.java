package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "hasFile", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge_auth")
@Entity
public class ChallengeAuth extends BaseLastModifiedEntity {

    @Column(name = "challenge_auth_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(nullable = false)
    private boolean hasFile;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "challenge_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Challenge challenge;

}
