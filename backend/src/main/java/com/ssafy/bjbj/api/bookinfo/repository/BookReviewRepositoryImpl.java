package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.QResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;

import javax.persistence.EntityManager;
import static com.ssafy.bjbj.api.bookinfo.entity.QBookReview.bookReview;

public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ResponseBookReviewDto findLatestBookReviewDtoByBookInfoAndMember(Long bookInfoSeq, Long memberSeq) {
        return queryFactory.select(new QResponseBookReviewDto(
                        bookReview.seq,
                        bookReview.starRating,
                        bookReview.summary,
                        bookReview.createdDate
                ))
                .from(bookReview)
                .where(bookReview.bookInfo.seq.eq(bookInfoSeq).and(bookReview.member.seq.eq(memberSeq)).and(bookReview.isDeleted.eq(false)))
                .orderBy(bookReview.createdDate.desc())
                .fetchFirst();
    }
}
