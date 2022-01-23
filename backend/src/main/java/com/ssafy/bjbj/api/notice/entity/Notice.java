package com.ssafy.bjbj.api.notice.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_notice")
@Entity
public class Notice extends BaseLastModifiedEntity {

    @Column(name = "notice_id")
    @GeneratedValue
    @Id
    private Long id;

    private String title;

    private String content;

    private Integer views;

    private boolean isDeleted;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "notice_comment_id")
    @OneToMany
    private List<NoticeComment> noticeComments = new ArrayList<>();

}
