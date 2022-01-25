package com.ssafy.bjbj.api.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.member.dto.response.QResponseMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;

import javax.persistence.EntityManager;

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
                        member.id,
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
