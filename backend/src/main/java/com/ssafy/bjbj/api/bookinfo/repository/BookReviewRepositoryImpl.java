package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.response.QResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.QResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.entity.QBookReview;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ssafy.bjbj.api.bookinfo.entity.QBookInfo.bookInfo;
import static com.ssafy.bjbj.api.member.entity.QMember.member;
import static com.ssafy.bjbj.api.bookinfo.entity.QBookReview.*;

public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ResponseBookReviewByMemberDto> findAllBookReviewDtoByMemberSeq(Long memberSeq) {
        return queryFactory.select(new QResponseBookReviewByMemberDto(
                        bookReview.seq,
                        bookReview.bookInfo.seq,
                        bookReview.member.seq,
                        bookReview.bookInfo.title,
                        bookReview.bookInfo.author,
                        bookReview.member.nickname,
                        bookReview.starRating,
                        bookReview.summary,
                        bookReview.createdDate
                ))
                .from(bookReview)
                .where(bookReview.member.seq.eq(memberSeq).and(bookReview.isDeleted.eq(false)))
                .orderBy(bookReview.createdDate.desc())
                .fetch();
    }

    @Override
    public List<ResponseBookReviewByBookInfoDto> findAllBookReviewDtoByBookInfoSeq(Long bookInfoSeq) {
        return queryFactory.select(new QResponseBookReviewByBookInfoDto(
                bookReview.bookInfo.seq,
                bookReview.member.seq,
                bookReview.member.nickname,
                bookReview.starRating,
                bookReview.summary,
                bookReview.createdDate
                ))
                .from(bookReview)
                .where(bookReview.bookInfo.seq.eq(bookInfoSeq).and(bookReview.isDeleted.eq(false)))
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
