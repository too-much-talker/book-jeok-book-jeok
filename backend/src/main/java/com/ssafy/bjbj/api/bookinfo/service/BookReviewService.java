package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

import java.util.List;

public interface BookReviewService {

    BookReview findBookReviewByBookSeq(Long bookReviewSeq);

    List<ResBookReviewByMemberDto> findAllBookReviewsByMemberSeq(Long memberSeq);

    List<ResBookReviewByBookInfoDto> findAllBookReviewsByBookInfoSeq(Long bookInfoSeq);

    ResBookReviewByMemberDto registerBookReview(ReqBookReviewDto reqBookReviewDto, Long memberSeq);

    ResModifiedBookReviewDto updateBookReview(ReqBookReviewDto reqBookReviewDto, Long memberSeq, Long bookReviewSeq);

    Integer countBookReviewsByMemberSeq(Long memberSeq);

    Integer countBookReviewsByBookInfoSeq(Long bookInfoSeq);

    void deleteBookReview(Long bookReviewSeq, Long memberSeq);

}
