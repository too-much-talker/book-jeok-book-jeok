package com.ssafy.bjbj.api.booklog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.ssafy.bjbj.api.booklog.entity.QBooklog.*;
import static com.ssafy.bjbj.api.booklog.entity.QLike.*;

public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countByMemberSeq(Long memberSeq) {
        return queryFactory
                .select(like.count().intValue())
                .from(like)
                .join(like.booklog, booklog).on(booklog.isOpen.isTrue().and(booklog.isDeleted.isFalse()))
                .where(like.member.seq.eq(memberSeq))
                .fetchOne();
    }

}
