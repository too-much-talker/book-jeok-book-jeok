package com.ssafy.bjbj.api.booklog.entity;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
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
    private boolean isDeleted;

    @Column(nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(nullable = false)
    @JoinColumn(name = "book_info_id")
    @ManyToOne(fetch = LAZY)
    private BookInfo bookInfo;

    @JoinColumns({
            @JoinColumn(name = "booklog_id", referencedColumnName = "booklog_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<Like> likes = new ArrayList<>();

}
