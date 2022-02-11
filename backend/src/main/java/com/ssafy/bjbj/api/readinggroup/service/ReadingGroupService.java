package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;

public interface ReadingGroupService {

    ReadingGroup register(ReqReadingGroupDto reqReadingGroupDto, Long memberSeq);

    ResReadingGroupDetailDto getResDto(Long readingGroupSeq);

}
