package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"seq", "starRating", "summary", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_review")
@Entity
public class BookReview extends BaseLastModifiedEntity {

    @Column(name = "bookreview_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @JoinColumn(name = "book_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookInfo bookInfo;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer starRating;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private boolean isDeleted;

    public void changeBookReviewDeleted(boolean isDeleted) {

        this.isDeleted = isDeleted;
    }

    @Builder
    public BookReview(Long seq, BookInfo bookInfo, Member member, Integer starRating, String summary, boolean isDeleted) {
        this.seq = seq;
        this.bookInfo = bookInfo;
        this.member = member;
        this.starRating = starRating;
        this.summary = summary;
        this.isDeleted = isDeleted;
    }
}
