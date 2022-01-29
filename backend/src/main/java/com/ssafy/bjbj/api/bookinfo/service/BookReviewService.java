package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;

public interface BookReviewService {

    boolean registerReview(RequestBookReviewDto requestBookReviewDto);

}
