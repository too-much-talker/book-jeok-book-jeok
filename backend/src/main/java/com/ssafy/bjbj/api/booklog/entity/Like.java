package com.ssafy.bjbj.api.booklog.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_like")
@Entity
public class Like {

    @Column(name = "booklog_id")
    @ManyToOne
    @Id
    private Booklog booklog;

    @Column(name = "member_id")
    @ManyToOne
    @Id
    private Member member;

}
