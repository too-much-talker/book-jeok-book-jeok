package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString(of = {"starRating", "summary", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BookInfoMemberSeq.class)
@Table(name = "tb_book_review")
@Entity
public class BookReview extends BaseLastModifiedEntity {

    @JoinColumn(name = "book_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    @Id
    private BookInfo bookInfo;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    @Id
    private Member member;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer starRating;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private boolean isDeleted;

}
