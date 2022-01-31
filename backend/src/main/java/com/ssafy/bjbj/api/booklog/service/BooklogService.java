package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;

public interface BooklogService {

    Long register(RequestBooklogDto requestBooklogDto);

    Long update(Long booklogSeq, RequestBooklogDto requestBooklogDto);

}
