package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.dto.response.MyReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ReadingGroupMiniDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadingGroupRepositoryCustom {

    Integer countReadingGroup();

    List<ReadingGroupMiniDto> findReadingGroupMiniDtos(Pageable pageable);

    ReadingGroup findNotEndReadingGroupBySeq(Long seq);

    long updateStatusPreToIng(int minNumOfMembers);

    long updateStatusPreToFail();

    long updateStatusIngToEnd();

    Integer countMyReadingGroupByMemberSeq(Long memberSeq);

    List<MyReadingGroupDto> findMyReadingGroupDtosByMemberSeq(Pageable pageable, Long memberSeq);

}
