package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_board")
@Entity
public class ReadingGroupBoard extends BaseLastModifiedEntity {

    @Column(name = "reading_group_board_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer views = 0;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "reading_group_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private ReadingGroup readingGroup;

    @JoinColumn(name = "reading_group_board_id")
    @OneToMany
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();

}
