package com.ssafy.bjbj.api.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.member.dto.request.QRequestMemberDto;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;

import javax.persistence.EntityManager;

import static com.ssafy.bjbj.api.member.entity.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public RequestMemberDto findMemberDtoByEmail(String email) {

        return queryFactory
                .select(new QRequestMemberDto(
                        member.email,
                        member.password,
                        member.name,
                        member.nickname,
                        member.phoneNumber))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

}
