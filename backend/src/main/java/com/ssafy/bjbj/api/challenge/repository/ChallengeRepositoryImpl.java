package com.ssafy.bjbj.api.challenge.repository;

import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.challenge.dto.response.*;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.common.enums.Status;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ssafy.bjbj.api.challenge.entity.QChallenge.*;
import static com.ssafy.bjbj.api.challenge.entity.QChallengeMember.*;
import static com.ssafy.bjbj.api.member.entity.QMember.*;

public class ChallengeRepositoryImpl implements ChallengeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChallengeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countChallengeMiniDto(boolean isAll) {
        JPAQuery<Integer> query = queryFactory
                .select(challenge.count().intValue())
                .from(challenge)
                .where(challenge.isDeleted.isFalse());

        if (!isAll) {
            query.where(challenge.status.eq(Status.PRE));
        }

        return query.fetchOne();
    }

    @Override
    public List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable) {
        JPAQuery<ChallengeMiniDto> query = queryFactory
                .select(new QChallengeMiniDto(
                        challenge.seq,
                        challenge.title,
                        challenge.deadline,
                        challenge.challengeMembers.size().intValue(),
                        challenge.views.intValue(),
                        challenge.maxMember.intValue()))
                .from(challenge)
                .join(challenge.challengeMembers, challengeMember)
                .groupBy(challenge)
                .where(challenge.isDeleted.isFalse())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize());

        if (!isAll) {
            query.where(challenge.status.eq(Status.PRE));
        }

        return query.fetch();
    }

    @Override
    public List<MyChallengeDto> findMyChallengeDtos(boolean isEnd, Pageable pageable, Long memberSeq) {
        JPAQuery<MyChallengeDto> query = queryFactory
                .select(new QMyChallengeDto(
                        challenge.seq,
                        challenge.title,
                        challenge.startDate,
                        challenge.endDate,
                        challenge.challengeMembers.size().intValue(),
                        challenge.maxMember.intValue(),
                        challenge.reward.intValue(),
                        challenge.status,
                        MathExpressions.round(
                                member.challengeAuths.size()
                                        .multiply(100.0)
                                        .divide(challenge.endDate.castToNum(Double.class).subtract(challenge.startDate.castToNum(Double.class))
                                                .add(1000000.0)
                                                .divide(1000000.0))
                                        .doubleValue(),
                                2)))
                .from(challenge)
                .join(challenge.challengeMembers, challengeMember)
                .join(challengeMember.member, member).on(challengeMember.member.seq.eq(memberSeq))
                .groupBy(challenge)
                .where(challenge.isDeleted.isFalse())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(challenge.createdDate.desc());

        if (isEnd) {
            query.where(challenge.status.eq(Status.END));
        } else {
            query.where(challenge.status.in(Status.PRE, Status.ING));
        }

        return query.fetch();
    }

    @Override
    public Double findAuthRateByMemberSeq(Long challengeSeq, Long memberSeq) {
        return queryFactory
                .select(
                        MathExpressions.round(
                                member.challengeAuths.size()
                                        .multiply(100.0)
                                        .divide(challenge.endDate.castToNum(Double.class).subtract(challenge.startDate.castToNum(Double.class))
                                                .add(1000000.0)
                                                .divide(1000000.0))
                                        .doubleValue(),
                                2))
                .from(challenge)
                .join(challenge.challengeMembers, challengeMember)
                .join(challengeMember.member, member).on(member.seq.eq(memberSeq))
                .where(challenge.seq.eq(challengeSeq).and(challenge.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public Optional<Challenge> findChallengeBySeq(Long challengeSeq) {
        return Optional.ofNullable(queryFactory
                .selectFrom(challenge)
                .where(challenge.seq.eq(challengeSeq).and(challenge.isDeleted.isFalse()))
                .fetchOne());
    }

    @Override
    public List<Challenge> findEndedChallenge() {
        return queryFactory
                .selectFrom(challenge)
                .where(challenge.endDate.before(LocalDateTime.now()).and(challenge.isDeleted.isFalse())
                        .and(challenge.isRewarded.isFalse()))
                .fetch();
    }

}
