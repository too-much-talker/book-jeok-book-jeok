package com.ssafy.bjbj.api.booklog.entity;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@ToString(of = {"seq", "title", "content", "summary", "starRating", "readDate", "isOpen", "views", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_booklog")
@Entity
public class Booklog extends BaseLastModifiedEntity {

    @Column(name = "booklog_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String summary;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer starRating;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime readDate;

    @Column(nullable = false)
    private boolean isOpen;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JoinColumn(name = "book_info_seq", nullable = false)
    @ManyToOne(fetch = LAZY)
    private BookInfo bookInfo;

    // 나를 like한 멤버들
    @JoinColumn(name = "booklog_seq")
    @OneToMany
    private List<Like> likes = new ArrayList<>();

}
