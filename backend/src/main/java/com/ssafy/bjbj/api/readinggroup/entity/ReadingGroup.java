package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.Status;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "views", "limitPoint", "maxMember", "deadline", "status", "startDate", "endDate", "readingGroupType", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group")
@Entity
public class ReadingGroup extends BaseLastModifiedEntity {

    @Column(name = "reading_group_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer limitPoint;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer maxMember;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingGroupType readingGroupType;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "reading_group_seq")
    @OneToMany
    private List<ReadingGroupBoard> readingGroupBoards = new ArrayList<>();

    @JoinColumn(name = "reading_group_seq")
    @OneToMany
    private List<ReadingGroupDate> readingGroupDates = new ArrayList<>();

    // 독서모임에 속한 멤버들
    @JoinColumn(name = "reading_group_seq")
    @OneToMany
    private List<ReadingGroupMember> readingGroupMembers = new ArrayList<>();

}
