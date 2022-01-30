package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

import java.util.List;

public interface BookReviewRepositoryCustom {

    List<ResponseBookReviewDto> findAllBookReviewDtoByMemberSeq(Long memberSeq);

    BookReview findLatestBookReviewByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);
}
