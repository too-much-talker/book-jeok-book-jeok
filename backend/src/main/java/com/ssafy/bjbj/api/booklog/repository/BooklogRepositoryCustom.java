package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.booklog.dto.response.LikeBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.MyBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.OpenBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.SearchBooklogDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BooklogRepositoryCustom {

    Integer countByOpenBooklogAndRecentOneWeek();

    List<OpenBooklogDto> findOpenBooklogDtos(Pageable pageable);

    Integer countMyBooklogByMemberSeq(boolean isAll, Long memberSeq);

    List<MyBooklogDto> findMyBooklogDtos(boolean isAll, Pageable pageable, Long memberSeq);

    Integer countSearchBooklogByKeyword(String keyword, String writer);

    List<SearchBooklogDto> findSearchBooklog(Pageable pageable, String keyword, String writer);

    List<LikeBooklogDto> findLikeBooklogDtos(Pageable pageable, Long memberSeq);

}
