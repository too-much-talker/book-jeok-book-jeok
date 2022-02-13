package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupListPageDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import org.springframework.data.domain.Pageable;

public interface ReadingGroupService {

    ReadingGroup register(ReqReadingGroupDto reqReadingGroupDto, Long memberSeq);

    ResReadingGroupDetailDto getResReadingGroupDetailDto(Long readingGroupSeq);

    ResReadingGroupListPageDto getReadingGroupListPageDto(Pageable pageable);

    boolean isMeetToday(Long readingGroupSeq, Long memberSeq);

    long chagneStatusPreToIng();

    void modify(Long readingGroupSeq, ReqReadingGroupDto reqReadingGroupDto, Long memberSeq);

    void removeOne(Long readingGroupSeq, Long memberSeq);

}
