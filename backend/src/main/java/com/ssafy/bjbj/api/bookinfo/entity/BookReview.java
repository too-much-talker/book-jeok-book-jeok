package com.ssafy.bjbj.api.bookinfo.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"seq", "starRating", "summary", "isDeleted"})
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_book_review")
@Entity
public class BookReview extends BaseLastModifiedEntity {

    @Column(name = "bookreview_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @JoinColumn(name = "book_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
    private BookInfo bookInfo;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne
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

}
