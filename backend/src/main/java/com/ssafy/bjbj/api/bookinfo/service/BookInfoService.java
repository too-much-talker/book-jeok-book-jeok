package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookListDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookListDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;

public interface BookInfoService {

    ResponseBookInfoDto findResponseBookInfoDtoBySeq(Long seq);

    ResBookListDto findResponseBookListDtosByRequest(ReqBookListDto requestBookInfoDto);

}
