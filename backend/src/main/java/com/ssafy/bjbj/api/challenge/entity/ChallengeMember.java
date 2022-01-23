package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChallengeMemberId.class)
@Table(name = "tb_challenge_member")
@Entity
public class ChallengeMember {

    @JoinColumn(name = "challenge_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private Challenge challenge;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member member;

}
