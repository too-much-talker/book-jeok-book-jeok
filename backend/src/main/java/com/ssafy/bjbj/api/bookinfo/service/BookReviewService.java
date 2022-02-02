package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

import java.util.List;

public interface BookReviewService {

    BookReview findBookReviewByBookSeq(Long bookReviewSeq);

    List<ResponseBookReviewByMemberDto> findAllBookReviewsByMemberSeq(Long memberSeq);

    List<ResponseBookReviewByBookInfoDto> findAllBookReviewsByBookInfoSeq(Long bookInfoSeq);

    ResponseBookReviewByMemberDto registerBookReview(RequestBookReviewDto requestBookReviewDto);

    boolean deleteBookReview(Long bookReviewSeq);
}
