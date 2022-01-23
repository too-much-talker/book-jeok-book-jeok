package com.ssafy.bjbj.api.challenge.entity;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_challenge_member")
@Entity
public class ChallengeMember {

    @Column(name = "challenge_id")
    @ManyToOne
    @Id
    private Challenge challenge;

    @Column(name = "member_id")
    @ManyToOne
    @Id
    private Member member;
}
