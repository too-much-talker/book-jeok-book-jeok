package com.ssafy.bjbj.api.readinggroup.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.readinggroup.dto.response.MyReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ReadingGroupMiniDto;
import com.ssafy.bjbj.api.readinggroup.entity.QReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.common.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.*;
import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroup.*;
import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroup.readingGroup;
import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroupDate.readingGroupDate;
import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroupMember.*;

@Slf4j
public class ReadingGroupRepositoryImpl implements ReadingGroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    public ReadingGroupRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Integer countReadingGroup() {
        return queryFactory
                .select(readingGroup.count().intValue())
                .from(readingGroup)
                .where(readingGroup.isDeleted.isFalse())
                .fetchOne();
    }

    @Override
    public List<ReadingGroupMiniDto> findReadingGroupMiniDtos(Pageable pageable) {
        List<ReadingGroupMiniDto> totalData = queryFactory.from(readingGroup)
                .join(readingGroup.readingGroupMembers, readingGroupMember)
                .where(readingGroup.isDeleted.isFalse())
                .orderBy(readingGroup.createdDate.desc())
                .transform(groupBy(readingGroup.seq).list((
                        Projections.fields(ReadingGroupMiniDto.class,
                                readingGroup.seq.as("readingGroupSeq"),
                                readingGroup.title,
                                readingGroup.member.seq.as("writerSeq"),
                                readingGroup.readingGroupType,
                                readingGroup.deadline,
                                readingGroup.startDate,
                                readingGroup.endDate,
                                list(readingGroupMember.member.seq).as("participantSeqs")
                        ))));

        int totalCnt = totalData.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int offset = (currentPage - 1) * pageSize;
        int totalPage = (int) Math.ceil((double) totalCnt / pageSize);
        log.debug("totalCnt = {}", totalCnt);
        log.debug("pageSize = {}", pageSize);
        log.debug("currentPage = {}", currentPage);
        log.debug("offset = {}", offset);
        log.debug("totalPage = {}", totalPage);

        if (currentPage < totalPage) {
            return totalData.subList(offset, offset + pageSize);
        } else if (currentPage == totalPage) {
            return totalData.subList(offset, offset + totalCnt - (currentPage - 1) * pageSize);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public ReadingGroup findNotEndReadingGroupBySeq(Long seq) {
        return queryFactory
                .selectFrom(readingGroup)
                .where(readingGroup.seq.eq(seq).and(readingGroup.endDate.after(LocalDateTime.now().minusDays(1L))))
                .fetchOne();
    }

    @Override
    public long updateStatusPreToIng(int minNumOfMembers) {
        return queryFactory
                .update(readingGroup)
                .set(readingGroup.status, Status.ING)
                .where(readingGroup.deadline.before(LocalDateTime.now().minusDays(1L))
                        .and(readingGroup.status.eq(Status.PRE))
                        .and(readingGroup.readingGroupMembers.size().goe(minNumOfMembers)))
                .execute();
    }

    @Override
    public long updateStatusPreToFail() {
        return queryFactory
                .update(readingGroup)
                .set(readingGroup.status, Status.FAIL)
                .where(readingGroup.deadline.before(LocalDateTime.now().minusDays(1L))
                        .and(readingGroup.status.eq(Status.PRE))
                        .and(readingGroup.readingGroupMembers.size().eq(1)))
                .execute();
    }

    @Override
    public boolean existReadingGroupByMemberSeq(Long memberSeq) {
        ReadingGroup readingGroup = queryFactory.selectFrom(QReadingGroup.readingGroup)
                .where(QReadingGroup.readingGroup.member.seq.eq(memberSeq).and(QReadingGroup.readingGroup.status.in(Status.PRE, Status.ING)))
                .fetchOne();

        if (readingGroup == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long updateStatusIngToEnd() {
        return queryFactory
                .update(readingGroup)
                .set(readingGroup.status, Status.END)
                .where(readingGroup.endDate.before(LocalDateTime.now().minusDays(1L))
                        .and(readingGroup.status.eq(Status.ING)))
                .execute();
    }

    @Override
    public Integer countMyReadingGroupByMemberSeq(Long memberSeq) {
        return queryFactory
                .select(readingGroup.count().intValue())
                .from(readingGroup)
                .join(readingGroup.readingGroupMembers, readingGroupMember)
                .where(readingGroupMember.member.seq.eq(memberSeq)
                        .and(readingGroup.isDeleted.isFalse()))
                .fetchOne();
    }

    @Override
    public List<MyReadingGroupDto> findMyReadingGroupDtosByMemberSeq(Pageable pageable, Long memberSeq) {
        List<MyReadingGroupDto> totalData = queryFactory.from(readingGroup, readingGroupMember)
                .join(readingGroup.readingGroupDates, readingGroupDate)
                .join(readingGroup.readingGroupMembers, readingGroupMember)
                .where(readingGroup.isDeleted.isFalse().and(readingGroupMember.member.seq.eq(memberSeq)))
                .orderBy(readingGroup.createdDate.desc())
                .transform(groupBy(readingGroup.seq).list((
                        Projections.fields(MyReadingGroupDto.class,
                                readingGroup.seq.as("readingGroupSeq"),
                                readingGroup.title,
                                readingGroup.status,
                                readingGroup.readingGroupType,
                                readingGroupMember.isReviewed,
                                list(readingGroupDate.conferenceDate).as("readingGroupDates")
                        ))));

        int totalCnt = totalData.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int offset = (currentPage - 1) * pageSize;
        int totalPage = (int) Math.ceil((double) totalCnt / pageSize);
        log.debug("totalCnt = {}", totalCnt);
        log.debug("pageSize = {}", pageSize);
        log.debug("currentPage = {}", currentPage);
        log.debug("offset = {}", offset);
        log.debug("totalPage = {}", totalPage);

        if (currentPage < totalPage) {
            return totalData.subList(offset, offset + pageSize);
        } else if (currentPage == totalPage) {
            return totalData.subList(offset, offset + totalCnt - (currentPage - 1) * pageSize);
        } else {
            return new ArrayList<>();
        }

    }

}
