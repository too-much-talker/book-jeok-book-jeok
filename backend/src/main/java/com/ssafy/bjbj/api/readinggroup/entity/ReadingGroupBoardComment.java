package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_board_comment")
@Entity
public class ReadingGroupBoardComment extends BaseLastModifiedEntity {

    @Column(name = "reading_group_board_comment_id")
    @GeneratedValue
    @Id
    private Long id;

    private String content;

    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(fetch = LAZY)
    private ReadingGroupBoard readingGroupBoard;
}
