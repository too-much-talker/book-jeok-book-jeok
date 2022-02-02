package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.ResBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;

public interface BooklogService {

    Long register(RequestBooklogDto requestBooklogDto);

    Long update(Long booklogSeq, RequestBooklogDto requestBooklogDto);

    void remove(Long booklogSeq, Long memberSeq);

    void changeIsOpen(Long booklogSeq, Long memberSeq, boolean isOpen);

    Booklog findBySeq(Long seq);

    ResBooklogDto getResBooklogDtoBooklog(Long booklogSeq, Long memberSeq);

}
