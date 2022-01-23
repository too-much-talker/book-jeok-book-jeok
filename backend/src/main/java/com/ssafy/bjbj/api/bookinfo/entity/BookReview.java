package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BookInfoMemberId.class)
@Table(name = "tb_book_review")
@Entity
public class BookReview extends BaseLastModifiedEntity {

    @JoinColumn(name = "book_info_id", nullable = false)
    @ManyToOne
    @Id
    private BookInfo bookInfo;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne
    @Id
    private Member member;

    @Column(nullable = false)
    private Integer starRating;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private boolean isDeleted;

}
