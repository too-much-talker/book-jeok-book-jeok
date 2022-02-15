package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChallengeMemberSeq.class)
@Table(name = "tb_challenge_member")
@Entity
public class ChallengeMember {

    @JoinColumn(name = "challenge_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private Challenge challenge;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member member;

    public static ChallengeMember create(Challenge challenge, Member member) {
        return ChallengeMember.builder()
                .challenge(challenge)
                .member(member)
                .build();
    }

    @Builder
    public ChallengeMember(Challenge challenge, Member member) {
        this.challenge = challenge;
        this.member = member;
    }

}
