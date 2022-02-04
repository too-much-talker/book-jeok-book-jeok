package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.booklog.dto.response.OpenBooklogDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BooklogRepositoryCustom {

    Integer countByOpenBooklogAndRecentOneWeek();

    List<OpenBooklogDto> findOpenBooklogDtos(Pageable pageable);

}
