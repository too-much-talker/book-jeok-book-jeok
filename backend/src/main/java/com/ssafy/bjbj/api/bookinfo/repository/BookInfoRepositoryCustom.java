package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;

public interface BookInfoRepositoryCustom {

    ResponseBookInfoDto findResponseBookInfoDtoBySeq(Long seq);
}
