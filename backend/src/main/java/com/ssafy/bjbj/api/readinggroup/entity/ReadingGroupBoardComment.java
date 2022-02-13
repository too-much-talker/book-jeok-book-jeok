package com.ssafy.bjbj.api.readinggroup.entity;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "content", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_board_comment")
@Entity
public class ReadingGroupBoardComment extends BaseLastModifiedEntity {

    @Column(name = "reading_group_board_comment_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "reading_group_board_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private ReadingGroupBoard readingGroupBoard;

    @QueryProjection
    @Builder
    public ReadingGroupBoardComment(String content, boolean isDeleted, Member member, ReadingGroupBoard readingGroupBoard) {
        this.content = content;
        this.isDeleted = isDeleted;
        this.member = member;
        this.readingGroupBoard = readingGroupBoard;
    }

    public void changeReadingGroupBoardComment(String content) {
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
