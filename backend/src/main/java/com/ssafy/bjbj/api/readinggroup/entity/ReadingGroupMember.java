package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ReadingGroupMemberId.class)
@Table(name = "tb_reading_group_Member")
@Entity
public class ReadingGroupMember {

    @JoinColumn(name = "reading_group_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private ReadingGroup readingGroup;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member member;

}
