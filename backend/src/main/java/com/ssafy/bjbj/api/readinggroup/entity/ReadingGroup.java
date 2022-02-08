package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.Status;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "views", "limitLevel", "maxMember", "deadline", "status", "startDate", "endDate", "readingGroupType", "isDeleted"})
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer limitLevel;

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

    @Builder
    public ReadingGroup(String title, String content, Integer views, Integer limitLevel, Integer maxMember, LocalDateTime deadline, Status status, LocalDateTime startDate, LocalDateTime endDate, ReadingGroupType readingGroupType, boolean isDeleted, Member member) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.limitLevel = limitLevel;
        this.maxMember = maxMember;
        this.deadline = deadline;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.readingGroupType = readingGroupType;
        this.isDeleted = isDeleted;
        this.member = member;
    }

}
