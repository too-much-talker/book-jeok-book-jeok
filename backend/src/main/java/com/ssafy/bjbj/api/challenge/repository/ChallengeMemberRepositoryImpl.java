package com.ssafy.bjbj.api.challenge.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.common.enums.Status;

import javax.persistence.EntityManager;

import static com.ssafy.bjbj.api.challenge.entity.QChallengeMember.*;

public class ChallengeMemberRepositoryImpl implements ChallengeMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChallengeMemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countAllByMemberSeq(boolean isEnd, Long memberSeq) {
        JPAQuery<Integer> query = queryFactory
                .select(challengeMember.count().intValue())
                .from(challengeMember)
                .where(challengeMember.member.seq.eq(memberSeq).and(challengeMember.challenge.isDeleted.isFalse()));

        if (isEnd) {
            query.where(challengeMember.challenge.status.eq(Status.END));
        } else {
            query.where(challengeMember.challenge.status.in(Status.PRE, Status.ING));
        }

        return query.fetchOne();
    }

}
