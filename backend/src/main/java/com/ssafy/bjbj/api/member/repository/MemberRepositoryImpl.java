package com.ssafy.bjbj.api.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.dto.QMemberDto;
import com.ssafy.bjbj.api.member.entity.QMember;

import javax.persistence.EntityManager;

import static com.ssafy.bjbj.api.member.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberDto findMemberDtoByEmail(String email) {

        return queryFactory
                .select(new QMemberDto(
                        member.email,
                        member.password,
                        member.name,
                        member.nickname))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }
}
