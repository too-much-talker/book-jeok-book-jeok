package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group")
@Entity
public class ReadingGroup extends BaseLastModifiedEntity {
    @Column(name = "reading_group_id")
    @GeneratedValue
    @Id
    private Long id;

    private String title;

    private String content;

    private Integer views;

    private Integer limitPoint;

    private Integer maxMember;

    private LocalDateTime deadline;

    private Status status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Type type;

    private boolean isDeleted;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "reading_group_board_id")
    @OneToMany
    private List<ReadingGroupBoard> readingGroupBoards = new ArrayList<>();

    @JoinColumn(name = "reading_group_board_comments_id")
    @OneToMany
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "readingGroup")
    private List<ReadingGroupDate> readingGroupDates = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "reading_group_id", referencedColumnName = "reading_group_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<ReadingGroupMember> readingGroupMembers = new ArrayList<>();
}
