package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.response.QResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.QResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

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
    public Integer countBookReviewsByMemberSeq(Long memberSeq) {
        return queryFactory.select(bookReview.count().intValue())
                .from(bookReview)
                .where(bookReview.member.seq.eq(memberSeq)
                        .and(bookReview.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public Integer countBookReviewsByBookInfoSeq(Long bookInfoSeq) {
        return queryFactory.select(bookReview.count().intValue())
                .from(bookReview)
                .where(bookReview.bookInfo.seq.eq(bookInfoSeq)
                        .and(bookReview.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public List<ResBookReviewByMemberDto> findAllBookReviewDtoByMemberSeq(Long memberSeq) {
        return queryFactory.select(new QResBookReviewByMemberDto(
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
                .where(bookReview.member.seq.eq(memberSeq).and(bookReview.isDeleted.isFalse()))
                .orderBy(bookReview.createdDate.desc())
                .fetch();
    }

    @Override
    public List<ResBookReviewByBookInfoDto> findAllBookReviewDtoByBookInfoSeq(Long bookInfoSeq) {
        return queryFactory
                .select(new QResBookReviewByBookInfoDto(
                        bookReview.seq,
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
        return queryFactory
                .selectFrom(bookReview)
                .join(bookReview.bookInfo, bookInfo).fetchJoin()
                .join(bookReview.member, member).fetchJoin()
                .where(bookReview.bookInfo.seq.eq(bookInfoSeq).and(bookReview.member.seq.eq(memberSeq)
                        .and(bookReview.isDeleted.eq(false))))
                .fetchOne();
    }

    @Override
    public long deleteAllByMemberSeq(Long memberSeq) {
        return queryFactory.update(bookReview)
                .set(bookReview.isDeleted, true)
                .where(bookReview.member.seq.eq(memberSeq).and(bookReview.isDeleted.isFalse()))
                .execute();
    }
}
