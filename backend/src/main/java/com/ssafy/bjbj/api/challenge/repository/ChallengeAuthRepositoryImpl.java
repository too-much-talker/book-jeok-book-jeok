package com.ssafy.bjbj.api.challenge.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.bjbj.api.challenge.entity.QChallengeAuth.*;

public class ChallengeAuthRepositoryImpl implements ChallengeAuthRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChallengeAuthRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LocalDateTime> findCreatedDateAllByChallengeSeqAndMemberSeq(Long challengeSeq, Long memberSeq) {
        return queryFactory
                .select(challengeAuth.createdDate)
                .from(challengeAuth)
                .where(challengeAuth.challenge.seq.eq(challengeSeq)
                        .and(challengeAuth.member.seq.eq(memberSeq))
                        .and(challengeAuth.isDeleted.isFalse()))
                .fetch();
    }

}
