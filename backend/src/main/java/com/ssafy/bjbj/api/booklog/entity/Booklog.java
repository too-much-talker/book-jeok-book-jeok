package com.ssafy.bjbj.api.booklog.entity;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_booklog")
@Entity
public class Booklog extends BaseLastModifiedEntity {

    @Column(name = "booklog_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private String summary;

    private Integer starRating;

    private LocalDateTime readDate;

    @Column(nullable = false)
    private boolean isOpen;

    @Column(nullable = false)
    private Integer views = 0;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "book_info_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private BookInfo bookInfo;

    // 나를 like한 멤버들
    @JoinColumn(name = "booklog_id")
    @OneToMany
    private List<Like> likes = new ArrayList<>();

}
