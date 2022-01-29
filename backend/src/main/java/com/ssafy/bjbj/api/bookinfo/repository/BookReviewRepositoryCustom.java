package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

public interface BookReviewRepositoryCustom {

    ResponseBookReviewDto findLatestBookReviewDtoByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);

    BookReview findLatestBookReviewByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);
}
