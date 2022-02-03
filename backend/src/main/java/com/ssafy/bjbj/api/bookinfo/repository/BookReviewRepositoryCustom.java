package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

import java.util.List;

public interface BookReviewRepositoryCustom {

    List<ResponseBookReviewByMemberDto> findAllBookReviewDtoByMemberSeq(Long memberSeq);

    List<ResponseBookReviewByBookInfoDto> findAllBookReviewDtoByBookInfoSeq(Long bookInfoSeq);

    BookReview findLatestBookReviewByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);
}
