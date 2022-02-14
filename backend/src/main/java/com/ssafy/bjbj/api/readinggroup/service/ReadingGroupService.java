package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResMyReadingGroupPageDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupListPageDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupReview;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ReadingGroupService {

    ReadingGroup register(ReqReadingGroupDto reqReadingGroupDto, Long memberSeq);

    ResReadingGroupDetailDto getResReadingGroupDetailDto(Long readingGroupSeq);

    ResReadingGroupListPageDto getReadingGroupListPageDto(Pageable pageable);

    boolean isMeetToday(Long readingGroupSeq, Long memberSeq);

    long changeReadingGroupStatus();

    void modify(Long readingGroupSeq, ReqReadingGroupDto reqReadingGroupDto, Long memberSeq);

    void removeOne(Long readingGroupSeq, Long memberSeq);

    ResMyReadingGroupPageDto getResMyReadingGroupPageDto(Pageable pageable, Long memberSeq);

    void reviewReadingGroupMember(Long readingGroupSeq, Map<String, ReadingGroupReview> reviews, Long memberSeq);

}
