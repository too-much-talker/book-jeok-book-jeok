package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.notice.entity.NoticeComment;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
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

    private String title;

    private String content;

    private Integer views;

    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private ReadingGroup readingGroup;

    @JoinColumn(name = "reading_group_board_comment_id")
    @OneToMany
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();
}
