package com.ssafy.bjbj.api.readinggroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.readinggroup.dto.response.QResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroupBoard.*;

public class ReadingGroupBoardRepositoryImpl implements ReadingGroupBoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReadingGroupBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countReadingGroupBoard(Long readingGroupSeq) {
        return queryFactory.select(readingGroupBoard.count().intValue())
                .from(readingGroupBoard)
                .where(readingGroupBoard.readingGroup.seq.eq(readingGroupSeq).and(readingGroupBoard.isDeleted.eq(false)))
                .fetchOne();
    }
    
    @Override
    public List<ResReadingGroupArticleDto> findReadingGroupDtos(Long readingGroupSeq, Pageable pageable) {
        return queryFactory.select(new QResReadingGroupArticleDto(
                        readingGroupBoard.member.seq,
                        readingGroupBoard.readingGroup.seq,
                        readingGroupBoard.seq,
                        readingGroupBoard.title,
                        readingGroupBoard.content,
                        readingGroupBoard.member.nickname,
                        readingGroupBoard.createdDate,
                        readingGroupBoard.lastModifiedDate,
                        readingGroupBoard.views))
                .from(readingGroupBoard)
                .where(readingGroupBoard.readingGroup.seq.eq(readingGroupSeq)
                        .and(readingGroupBoard.isDeleted.eq(false)))
                .offset(((long) pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .orderBy(readingGroupBoard.seq.desc())
                .fetch();
    }

    @Override
    public long deleteAllByReadingGroupSeq(Long readingGroupSeq) {
        return queryFactory
                .update(readingGroupBoard)
                .set(readingGroupBoard.isDeleted, true)
                .where(readingGroupBoard.readingGroup.seq.eq(readingGroupSeq))
                .execute();
    }

    @Override
    public long deleteAllByMemberSeq(Long memberSeq) {
        return queryFactory.update(readingGroupBoard)
                .set(readingGroupBoard.isDeleted, true)
                .where(readingGroupBoard.member.seq.eq(memberSeq).and(readingGroupBoard.isDeleted.isFalse()))
                .execute();
    }
}
