package com.ssafy.bjbj.api.readinggroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class ReadingGroupBoardRepositoryImpl implements ReadingGroupBoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReadingGroupBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
