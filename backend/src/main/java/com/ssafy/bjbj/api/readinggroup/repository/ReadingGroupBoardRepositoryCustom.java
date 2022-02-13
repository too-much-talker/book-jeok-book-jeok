package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReadingGroupBoardRepositoryCustom {

    Integer countReadingGroupBoard(Long readingGroupSeq);

    List<ResReadingGroupArticleDto> findReadingGroupDtos(Long readingGroupSeq, Pageable pageable);

    long deleteAllByReadingGroupSeq(Long readingGroupSeq);

}
