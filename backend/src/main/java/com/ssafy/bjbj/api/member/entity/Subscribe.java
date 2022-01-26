package com.ssafy.bjbj.api.member.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@IdClass(SubscribeId.class)
@Table(name = "tb_subscribe")
@Entity
public class Subscribe {

    @JoinColumn(name = "from_member_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member fromMember;

    @JoinColumn(name = "to_member_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member toMember;

}