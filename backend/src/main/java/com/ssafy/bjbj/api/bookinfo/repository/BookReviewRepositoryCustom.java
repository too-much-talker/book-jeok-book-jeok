package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;

public interface BookReviewRepositoryCustom {

    ResponseBookReviewDto findLatestBookReviewDtoByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);
}
