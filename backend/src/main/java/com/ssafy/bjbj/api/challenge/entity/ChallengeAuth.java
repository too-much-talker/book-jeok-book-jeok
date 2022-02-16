package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "hasFile", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge_auth")
@Entity
public class ChallengeAuth extends BaseLastModifiedEntity {

    @Column(name = "challenge_auth_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
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

    @Builder
    public ChallengeAuth(String title, String content, boolean hasFile, boolean isDeleted, Member member, Challenge challenge) {
        this.title = title;
        this.content = content;
        this.hasFile = hasFile;
        this.isDeleted = isDeleted;
        this.member = member;
        this.challenge = challenge;
    }

}
