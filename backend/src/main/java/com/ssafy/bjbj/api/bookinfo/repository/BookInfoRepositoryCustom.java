package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookSearchDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoSmallDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoDto;

import java.util.List;

public interface BookInfoRepositoryCustom {

    ResBookInfoDto findResBookInfoDtoBySeq(Long seq);

    List<ResBookInfoSmallDto> findListByRequest(ReqBookSearchDto reqBookInfoDto);

}
