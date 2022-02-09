package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.*;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import org.springframework.data.domain.Pageable;

public interface BooklogService {

    Booklog register(ReqBooklogDto reqBooklogDto);

    Booklog update(Long booklogSeq, ReqBooklogDto reqBooklogDto);

    void remove(Long booklogSeq, Long memberSeq);

    void changeIsOpen(Long booklogSeq, Long memberSeq, boolean isOpen);

    Booklog findBySeq(Long seq);

    ResBooklogDto getResBooklogDtoBooklog(Long booklogSeq, Long memberSeq);

    ResOpenBooklogPageDto getResOpenBooklogListDto(Pageable pageable);

    ResMyBooklogPageDto getResMyBooklogPageDto(boolean isAll, Pageable pageable, Long memberSeq);

    ResSearchBooklogPageDto getResSearchBooklogPageDto(Pageable pageable, String keyword, String writer);

    ResLikeBooklogPageDto getResLikeBooklogPageDto(Pageable pageable, Long memberSeq);

    ResOpenBooklogPageByBookInfoDto getResOpenBooklogPageByBookInfoDto(Long bookInfoSeq, Pageable pageable);

}
