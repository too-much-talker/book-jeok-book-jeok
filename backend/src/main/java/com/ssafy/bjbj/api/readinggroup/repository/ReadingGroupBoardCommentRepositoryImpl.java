package com.ssafy.bjbj.api.readinggroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.readinggroup.dto.response.QResReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.entity.QReadingGroupBoardComment;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroupBoardComment.*;

public class ReadingGroupBoardCommentRepositoryImpl implements ReadingGroupBoardCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReadingGroupBoardCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countReadingGroupBoardCommentsByReadingGroupBoard(Long readingGroupArticleSeq) {
        return queryFactory.select(readingGroupBoardComment.count().intValue())
                .from(readingGroupBoardComment)
                .where(readingGroupBoardComment.readingGroupBoard.seq.eq(readingGroupArticleSeq)
                        .and(readingGroupBoardComment.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public List<ResReadingGroupBoardCommentDto> findReadingGroupBoardCommentByReadingGroupBoardSeq(Long readingGroupArticleSeq) {
        return queryFactory.select(new QResReadingGroupBoardCommentDto(
                        readingGroupBoardComment.seq,
                        readingGroupBoardComment.member.nickname,
                        readingGroupBoardComment.content,
                        readingGroupBoardComment.createdDate,
                        readingGroupBoardComment.lastModifiedDate
                ))
                .from(readingGroupBoardComment)
                .where(readingGroupBoardComment.readingGroupBoard.seq.eq(readingGroupArticleSeq)
                        .and(readingGroupBoardComment.isDeleted.isFalse()))
                .orderBy(readingGroupBoardComment.createdDate.desc())
                .fetch();
    }
}
