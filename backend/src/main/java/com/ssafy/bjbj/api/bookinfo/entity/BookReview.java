package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_review")
@Entity
public class BookReview extends BaseLastModifiedEntity {

    @Column(name = "book_info_id")
    @ManyToOne
    @Id
    private BookInfo bookInfo;

    @Column(name = "member_id")
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
