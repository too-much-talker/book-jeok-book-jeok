package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;

public interface BookReviewService {

    ResponseBookReviewDto registerBookReview(RequestBookReviewDto requestBookReviewDto);

}
