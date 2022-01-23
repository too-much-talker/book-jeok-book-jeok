package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_Member")
@Entity
public class ReadingGroupMember extends BaseLastModifiedEntity {

    @Column(name = "reading_group_id")
    @ManyToOne
    @Id
    private ReadingGroup readingGroup;

    @Column(name = "member_id")
    @ManyToOne
    @Id
    private Member member;

}
