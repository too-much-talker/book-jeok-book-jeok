package com.ssafy.bjbj.api.member.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.QActivityCountDto;
import com.ssafy.bjbj.api.member.dto.response.QResponseMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ssafy.bjbj.api.member.entity.QActivity.*;
import static com.ssafy.bjbj.api.member.entity.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ResponseMemberDto findResponseMemberDtoByEmail(String email) {
        return queryFactory
                .select(new QResponseMemberDto(
                        member.seq,
                        member.email,
                        member.password,
                        member.name,
                        member.nickname,
                        member.phoneNumber))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    @Override
    public List<ActivityCountDto> findAllActivityCountDtoBySeq(Long seq) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                activity.time,
                "%Y-%m-%d");

        return queryFactory
                .select(new QActivityCountDto(
                        formattedDate.as("date"),
                        formattedDate.count().intValue().as("count")
                        ))
                .from(activity)
                .join(activity.member, member)
                .where(activity.member.seq.eq(seq))
                .groupBy(formattedDate)
                .orderBy(formattedDate.desc())
                .fetch();
    }

}
