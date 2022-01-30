package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.QResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.entity.QBookReview;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ssafy.bjbj.api.bookinfo.entity.QBookInfo.bookInfo;
import static com.ssafy.bjbj.api.bookinfo.entity.QBookReview.bookReview;
import static com.ssafy.bjbj.api.member.entity.QMember.member;

public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseBookReviewDto> findAllBookReviewDtoByMemberSeq(Long memberSeq) {
        return queryFactory.select(new QResponseBookReviewDto(
                        bookReview.seq,
                        bookReview.starRating,
                        bookReview.summary,
                        bookReview.createdDate
                ))
                .from(bookReview)
                .where(bookReview.member.seq.eq(memberSeq))
                .orderBy(bookReview.createdDate.desc())
                .fetch();
    }

    @Override
    public BookReview findLatestBookReviewByBookInfoAndMember(Long bookInfoSeq, Long memberSeq) {
        BookReview bookReview = queryFactory.selectFrom(QBookReview.bookReview)
                .join(QBookReview.bookReview.bookInfo, bookInfo).fetchJoin()
                .join(QBookReview.bookReview.member, member).fetchJoin()
                .where(QBookReview.bookReview.bookInfo.seq.eq(bookInfoSeq).and(QBookReview.bookReview.member.seq.eq(memberSeq)
                        .and(QBookReview.bookReview.isDeleted.eq(false))))
                .fetchOne();
        return bookReview;
    }
}
