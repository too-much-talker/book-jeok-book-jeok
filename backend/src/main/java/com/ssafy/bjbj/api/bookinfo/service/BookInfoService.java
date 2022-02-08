package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookSearchDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookListDto;

public interface BookInfoService {

    ResBookInfoDto findResBookInfoDtoBySeq(Long seq);

    ResBookListDto findResBookSearchDto(ReqBookSearchDto requestBookInfoDto);

}
