package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookListDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoSmallDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;

import java.util.List;

public interface BookInfoRepositoryCustom {

    ResponseBookInfoDto findResponseBookInfoDtoBySeq(Long seq);

    List<ResBookInfoSmallDto> findListByRequest(ReqBookListDto reqBookInfoDto);
}
