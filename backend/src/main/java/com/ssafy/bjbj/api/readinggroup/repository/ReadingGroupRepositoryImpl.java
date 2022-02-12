package com.ssafy.bjbj.api.readinggroup.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.readinggroup.dto.response.ReadingGroupMiniDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.*;
import static com.ssafy.bjbj.api.readinggroup.entity.QReadingGroup.*;
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

}
