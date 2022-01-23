package com.ssafy.bjbj.api.notice.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
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

    @JoinColumn(name = "notice_id")
    @OneToMany
    private List<NoticeComment> noticeComments = new ArrayList<>();

}
