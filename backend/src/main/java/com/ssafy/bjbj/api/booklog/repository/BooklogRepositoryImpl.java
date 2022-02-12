package com.ssafy.bjbj.api.booklog.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.booklog.dto.response.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.bjbj.api.bookinfo.entity.QBookInfo.*;
import static com.ssafy.bjbj.api.booklog.entity.QBooklog.booklog;
import static com.ssafy.bjbj.api.booklog.entity.QLike.*;
import static com.ssafy.bjbj.api.member.entity.QMember.*;

public class BooklogRepositoryImpl implements BooklogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BooklogRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countByOpenBooklogAndRecentOneWeek() {
        return queryFactory
                .select(booklog.count().intValue())
                .from(booklog)
                .where(booklog.createdDate.between(LocalDateTime.now().minusWeeks(1), LocalDateTime.now())
                        .and(booklog.isOpen.isTrue())
                        .and(booklog.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public List<OpenBooklogDto> findOpenBooklogDtos(Pageable pageable) {
        JPAQuery<OpenBooklogDto> query = queryFactory
                .select(new QOpenBooklogDto(
                        booklog.seq,
                        member.nickname,
                        booklog.title,
                        booklog.content.substring(0, 50),
                        booklog.likes.size().as("likes"),
                        booklog.createdDate,
                        bookInfo.largeImgUrl.as("imgUrl")))
                .from(booklog)
                .join(booklog.member, member)
                .leftJoin(booklog.likes, like).on(like.booklog.eq(booklog)).groupBy(booklog)
                .join(booklog.bookInfo, bookInfo)
                .where(booklog.createdDate.between(LocalDateTime.now().minusWeeks(1), LocalDateTime.now())
                        .and(booklog.isOpen.isTrue())
                        .and(booklog.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize());

        // 정렬
        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equals("like")) {
                query.orderBy(booklog.likes.size().desc()).orderBy(booklog.createdDate.desc());
            } else {
                query.orderBy(booklog.createdDate.desc()).orderBy(booklog.likes.size().desc());
            }
        }

        List<OpenBooklogDto> openBooklogDtos = query.fetch();
        return openBooklogDtos;
    }

    @Override
    public Integer countMyBooklogByMemberSeq(boolean isAll, Long memberSeq) {
        JPAQuery<Integer> query = queryFactory
                .select(booklog.count().intValue())
                .from(booklog)
                .where(booklog.member.seq.eq(memberSeq)
                        .and(booklog.isDeleted.isFalse()));

        if (!isAll) {
            query.where(booklog.isOpen.isTrue());
        }

        return query.fetchOne();
    }

    @Override
    public List<MyBooklogDto> findMyBooklogDtos(boolean isAll, Pageable pageable, Long memberSeq) {
        JPAQuery<MyBooklogDto> query = queryFactory
                .select(new QMyBooklogDto(
                        booklog.seq,
                        booklog.title,
                        booklog.createdDate,
                        booklog.isOpen,
                        bookInfo.largeImgUrl.as("imgUrl")))
                .from(booklog)
                .join(booklog.bookInfo, bookInfo)
                .where(booklog.member.seq.eq(memberSeq)
                        .and(booklog.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(booklog.createdDate.desc());

        if (!isAll) {
            query.where(booklog.isOpen.isTrue());
        }

        List<MyBooklogDto> myBooklogDtos = query.fetch();
        return myBooklogDtos;
    }

    @Override
    public Integer countSearchBooklogByKeyword(String keyword, String writer) {
        JPAQuery<Integer> query = queryFactory
                .select(booklog.count().intValue())
                .from(booklog)
                .join(booklog.member, member)
                .where(booklog.isOpen.isTrue()
                        .and(booklog.isDeleted.isFalse()));

        if (keyword != null) {
            query.where(booklog.title.contains(keyword).or(booklog.content.contains(keyword)));
        }

        if (writer != null) {
            query.where(booklog.member.nickname.eq(writer));
        }

        return query.fetchOne();
    }

    @Override
    public List<SearchBooklogDto> findSearchBooklog(Pageable pageable, String keyword, String writer) {
        JPAQuery<SearchBooklogDto> query = queryFactory
                .select(new QSearchBooklogDto(
                        booklog.seq,
                        member.nickname,
                        booklog.title,
                        booklog.content.substring(0, 50),
                        booklog.likes.size().as("likes"),
                        booklog.createdDate,
                        bookInfo.largeImgUrl.as("imgUrl")))
                .from(booklog)
                .join(booklog.member, member)
                .leftJoin(booklog.likes, like).on(like.booklog.eq(booklog)).groupBy(booklog)
                .join(booklog.bookInfo, bookInfo)
                .where(booklog.isOpen.isTrue()
                        .and(booklog.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(booklog.createdDate.desc());

        if (keyword != null) {
            query.where(booklog.title.contains(keyword).or(booklog.content.contains(keyword)));
        }

        if (writer != null) {
            query.where(booklog.member.nickname.eq(writer));
        }

        List<SearchBooklogDto> searchBooklogDtos = query.fetch();
        return searchBooklogDtos;
    }

    @Override
    public List<LikeBooklogDto> findLikeBooklogDtos(Pageable pageable, Long memberSeq) {
        return queryFactory
                .select(new QLikeBooklogDto(
                        booklog.seq,
                        booklog.title,
                        booklog.createdDate,
                        booklog.isOpen,
                        bookInfo.largeImgUrl.as("imgUrl")))
                .from(booklog)
                .join(booklog.bookInfo, bookInfo)
                .join(booklog.likes, like).on(like.member.seq.eq(memberSeq))
                .where(booklog.isOpen.isTrue()
                        .and(booklog.isDeleted.isFalse()))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(booklog.createdDate.desc())
                .fetch();
    }

    @Override
    public Integer countOpenBooklogByBookInfoSeq(Long bookInfoSeq) {
        return queryFactory
                .select(booklog.count().intValue())
                .from(booklog)
                .where(booklog.bookInfo.seq.eq(bookInfoSeq)
                        .and(booklog.isOpen.isTrue())
                        .and(booklog.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public List<OpenBooklogByBookInfoDto> findOpenBooklogByBookInfoDtos(Long bookInfoSeq, Pageable pageable) {
        return queryFactory
                .select(new QOpenBooklogByBookInfoDto(
                        booklog.seq,
                        booklog.title,
                        booklog.content,
                        booklog.createdDate,
                        booklog.isOpen,
                        booklog.bookInfo.largeImgUrl
                ))
                .from(booklog)
                .join(booklog.bookInfo, bookInfo)
                .where(booklog.bookInfo.seq.eq(bookInfoSeq).and(booklog.isOpen.isTrue().and(booklog.isDeleted.isFalse())))
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(booklog.createdDate.desc())
                .fetch();
    }

}
