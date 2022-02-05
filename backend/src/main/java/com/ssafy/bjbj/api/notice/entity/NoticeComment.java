package com.ssafy.bjbj.api.notice.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@ToString(of = {"seq", "content", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_notice_comment")
@Entity
public class NoticeComment extends BaseLastModifiedEntity {

    @Column(name = "notice_comment_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "notice_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Notice notice;

}
