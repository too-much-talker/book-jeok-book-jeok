package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

import java.util.List;

public interface BookReviewRepositoryCustom {

    List<ResBookReviewByMemberDto> findAllBookReviewDtoByMemberSeq(Long memberSeq);

    List<ResBookReviewByBookInfoDto> findAllBookReviewDtoByBookInfoSeq(Long bookInfoSeq);

    Integer countBookReviewsByMemberSeq(Long memberSeq);

    Integer countBookReviewsByBookInfoSeq(Long bookInfoSeq);

    BookReview findLatestBookReviewByBookInfoAndMember(Long bookInfoSeq, Long memberSeq);

}
