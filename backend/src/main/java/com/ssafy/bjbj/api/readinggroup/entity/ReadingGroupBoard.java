package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "title", "content", "views", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_board")
@Entity
public class ReadingGroupBoard extends BaseLastModifiedEntity {

    @Column(name = "reading_group_board_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "reading_group_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private ReadingGroup readingGroup;

    @JoinColumn(name = "reading_group_board_seq")
    @OneToMany
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();

    @Builder
    public ReadingGroupBoard(String title, String content, Integer views, boolean isDeleted, Member member, ReadingGroup readingGroup) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.isDeleted = isDeleted;
        this.member = member;
        this.readingGroup = readingGroup;
    }
}
