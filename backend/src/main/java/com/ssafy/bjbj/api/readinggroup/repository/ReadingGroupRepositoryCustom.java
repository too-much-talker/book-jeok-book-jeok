package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.dto.response.ReadingGroupMiniDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadingGroupRepositoryCustom {

    Integer countReadingGroup();

    List<ReadingGroupMiniDto> findReadingGroupMiniDtos(Pageable pageable);

}
