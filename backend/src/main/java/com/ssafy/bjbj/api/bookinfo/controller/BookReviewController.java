package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookreviews")
@RestController
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody RequestBookReviewDto requestBookReviewDto, Errors errors, Authentication authentication) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean isAuthenticatedMember = details.getMember().getSeq().equals(requestBookReviewDto.getMemberSeq());

        if (!isAuthenticatedMember) {
            // 작성한 멤버의 정보가 요청한 멤버의 정보가 일치하지 않은 경우
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "인증되지 않은 회원입니다");
        } else if (errors.hasErrors()) {
            status = HttpStatus.BAD_REQUEST.value();
            if (errors.hasFieldErrors()) {
                // field error
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("msg", errors.getFieldError().getDefaultMessage());
            } else {
                // global error
                responseData.put("msg", "global error");
            }
        } else {
            // 북리뷰를 작성
            ResponseBookReviewByMemberDto responseBookReviewDto = bookReviewService.registerBookReview(requestBookReviewDto);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "새로운 리뷰를 작성했습니다.");
            responseData.put("reviewInfo", responseBookReviewDto);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/members/{memberSeq}")
    public BaseResponseDto getMyBookReviews(@PathVariable Long memberSeq, Authentication authentication) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean isAuthenticatedMember = details.getMember().getSeq().equals(memberSeq);

        if (!isAuthenticatedMember) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "인증되지 않은 회원입니다");
        } else {
            List<ResponseBookReviewByMemberDto> reviewsByMemberSeq = bookReviewService.findAllBookReviewsByMemberSeq(memberSeq);

            if (reviewsByMemberSeq.size() == 0) {
                // 북리뷰가 하나도 없을경우
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "작성한 북리뷰가 하나도 없습니다");
            } else {
            // 북리뷰 조회 성공
            status = HttpStatus.OK.value();
            responseData.put("msg", "작성한 리뷰들이 있습니다");
            responseData.put("myBookReviews",reviewsByMemberSeq);
            responseData.put("totalCnt", bookReviewService.countBookReviewsByMemberSeq(memberSeq));
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/bookinfos/{bookInfoSeq}")
    public BaseResponseDto getBookInfoBookReviews(@PathVariable Long bookInfoSeq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        List<ResponseBookReviewByBookInfoDto> reviewsByBookInfoSeq = bookReviewService.findAllBookReviewsByBookInfoSeq(bookInfoSeq);

        if (reviewsByBookInfoSeq.size() == 0) {
            // 북리뷰가 하나도 없을경우
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "작성된 북리뷰가 하나도 없습니다");
        } else {
            // 북리뷰 조회 성공
            status = HttpStatus.OK.value();
            responseData.put("msg", "작성된 리뷰들이 있습니다");
            responseData.put("myBookReviews",reviewsByBookInfoSeq);
            responseData.put("totalCnt", bookReviewService.countBookReviewsByBookInfoSeq(bookInfoSeq));
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PutMapping("/{bookReviewSeq}")
    public BaseResponseDto update(@Valid @RequestBody RequestBookReviewDto requestBookReviewDto, Errors errors, @PathVariable Long bookReviewSeq, Authentication authentication) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean isAuthenticatedMember = details.getMember().getSeq().equals(requestBookReviewDto.getMemberSeq());

        if (!isAuthenticatedMember) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "인증되지 않은 회원입니다");
        } else if (errors.hasErrors()) {
            status = HttpStatus.BAD_REQUEST.value();
            if (errors.hasFieldErrors()) {
                // field error
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("msg", errors.getFieldError().getDefaultMessage());
            } else {
                // global error
                responseData.put("msg", "global error");
            }
        } else {
            ResModifiedBookReviewDto modifiedBookReviewDto = bookReviewService.updateBookReview(requestBookReviewDto);

            if (modifiedBookReviewDto == null) {
                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", "요청한 북리뷰가 없습니다");
            } else {
                status = HttpStatus.OK.value();
                responseData.put("msg", "수정되었습니다");
                responseData.put("modifiedBookReview", modifiedBookReviewDto);
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @DeleteMapping("/{bookReviewSeq}")
    public BaseResponseDto delete(@PathVariable Long bookReviewSeq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        if (!bookReviewService.deleteBookReview(bookReviewSeq)){
            // 삭제할 북리뷰가 없는경우
            status = HttpStatus.NOT_FOUND.value();
            responseData.put("msg", "삭제 가능한 북리뷰가 없습니다.");
        } else {
            // 북리뷰 삭제 성공
            status = HttpStatus.OK.value();
            responseData.put("msg", "리뷰를 삭제했습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}

