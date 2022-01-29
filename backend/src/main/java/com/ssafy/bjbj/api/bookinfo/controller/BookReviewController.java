package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookreviews")
@RestController
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @PostMapping
    public BaseResponseDto reviewBookRegister(@Valid @RequestBody RequestBookReviewDto requestBookReviewDto, Errors errors, Authentication authentication) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean isAuthenticatedMember = details.getMember().getSeq().equals(requestBookReviewDto.getMemberSeq());

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
        } else if (!isAuthenticatedMember) {
            // 작성한 멤버의 정보가 요청한 멤버의 정보가 일치하지 않은 경우
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "인증되지 않은 회원입니다");

        } else {

            // 북리뷰를 작성
            ResponseBookReviewDto responseBookReviewDto = bookReviewService.registerBookReview(requestBookReviewDto);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "새로운 리뷰를 작성했습니다.");
            responseData.put("reviewInfo", responseBookReviewDto);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();

    }
}

