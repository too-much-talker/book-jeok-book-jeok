package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookinfos/{bookinfo_seq}/reviews")
@RestController
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @PostMapping("")
    public BaseResponseDto reviewRegister(@Valid @RequestBody RequestBookReviewDto requestBookReviewDto, Errors errors, @PathVariable Long bookinfo_seq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        boolean canReview = bookReviewService.registerReview(requestBookReviewDto);

        if (errors.hasErrors()) {
            status = HttpStatus.BAD_REQUEST.value();
            if (errors.hasFieldErrors()) {
                // field error
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("msg", errors.getFieldError().getDefaultMessage());
            } else {
                // global error
                responseData.put("msg", "global error");
            }
        } else if (!canReview){
            // 이미 동일한 유저가 리뷰를 더 작성하려 했을 경우
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 작성하신 리뷰가 있어 새로운 리뷰로 대체했습니다.");
        } else {
            // 패스워드에 이메일이 포함된 경우
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "새로운 리뷰를 작성했습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();

    }
}
