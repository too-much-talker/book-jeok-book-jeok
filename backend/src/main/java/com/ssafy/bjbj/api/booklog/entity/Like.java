package com.ssafy.bjbj.api.booklog.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BooklogMemberSeq.class)
@Table(name = "tb_like")
@Entity
public class Like {

    @JoinColumn(name = "booklog_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private Booklog booklog;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member member;

}
