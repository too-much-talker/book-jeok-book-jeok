package com.ssafy.bjbj.api.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_subscribe")
@Entity
public class Subscribe {

    @Column(name = "from_member_id")
    @ManyToOne
    @Id
    private Member fromMember;

    @Column(name = "to_member_id")
    @ManyToOne
    @Id
    private Member toMember;

}
