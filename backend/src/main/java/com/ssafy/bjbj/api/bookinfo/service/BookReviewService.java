package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;

public interface BookReviewService {

    BookReview findBookReviewBySeq(Long bookReviewSeq);

    ResponseBookReviewDto registerBookReview(RequestBookReviewDto requestBookReviewDto);

    boolean deleteBookReview(Long bookReviewSeq);
}
