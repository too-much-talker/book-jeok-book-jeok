package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookReviewException;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqBookReviewDto reqBookReviewDto, Errors errors, Authentication authentication) {
        log.debug("BookReviewController.register() 책 리뷰 작성 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

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
        } else {
            // 북리뷰를 작성
            Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
            try {
                ResBookReviewByMemberDto resBookReviewByMemberDto = bookReviewService.registerBookReview(reqBookReviewDto, memberSeq);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "새로운 리뷰를 작성했습니다.");
                responseData.put("reviewInfo", resBookReviewByMemberDto);
            } catch (NotFoundBookInfoException e) {
                log.error("책 정보를 찾지 못했습니다.");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                log.error("서버 에러 발생");

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "요청을 수행할 수 없습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/me")
    public BaseResponseDto getMyBookReviews(Authentication authentication) {
        log.debug("BookReviewController.getMyBookReviews 나의 책 리뷰 목록 조회 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        List<ResBookReviewByMemberDto> resBookReviewByMemberDtos = bookReviewService.findAllBookReviewsByMemberSeq(memberSeq);

        if (resBookReviewByMemberDtos.size() == 0) {
            // 북리뷰가 하나도 없을경우
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "작성한 북리뷰가 하나도 없습니다");
        } else {
            // 북리뷰 조회 성공
            status = HttpStatus.OK.value();
            responseData.put("msg", "작성한 리뷰들이 있습니다");
            responseData.put("myBookReviews", resBookReviewByMemberDtos);
            responseData.put("totalCnt", bookReviewService.countBookReviewsByMemberSeq(memberSeq));
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/bookinfos/{bookInfoSeq}")
    public BaseResponseDto getBookReviewByBookInfo(@PathVariable Long bookInfoSeq) {
        log.debug("BookReviewController.getBookReviewByBookInfo() 책 정보로 책 리뷰 목록 조회 API 호출");
        
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        List<ResBookReviewByBookInfoDto> resBookReviewByBookInfoDtos = bookReviewService.findAllBookReviewsByBookInfoSeq(bookInfoSeq);

        if (resBookReviewByBookInfoDtos.size() == 0) {
            // 북리뷰가 하나도 없을경우
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "작성된 북리뷰가 하나도 없습니다");
        } else {
            // 북리뷰 조회 성공
            status = HttpStatus.OK.value();
            responseData.put("msg", "작성된 리뷰들이 있습니다");
            responseData.put("myBookReviews", resBookReviewByBookInfoDtos);
            responseData.put("totalCnt", bookReviewService.countBookReviewsByBookInfoSeq(bookInfoSeq));
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PutMapping("/{bookReviewSeq}")
    public BaseResponseDto update(@Valid @RequestBody ReqBookReviewDto reqBookReviewDto, Errors errors, @PathVariable Long bookReviewSeq, Authentication authentication) {
        log.debug("BookReviewController.update() 책 리뷰 수정 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

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
        } else {
            Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
            try {
                ResModifiedBookReviewDto modifiedBookReviewDto = bookReviewService.updateBookReview(reqBookReviewDto, memberSeq, bookReviewSeq);

                status = HttpStatus.OK.value();
                responseData.put("msg", "리뷰를 수정하였습니다");
                responseData.put("modifiedBookReview", modifiedBookReviewDto);
            } catch (NotEqualMemberException e) {
                log.error("책 리뷰 작성자가 다릅니다.");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (NotFoundBookReviewException e) {
                log.error("책 리뷰를 찾지 못했습니다.");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                log.error("서버 에러 발생");

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "요청을 수행할 수 없습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @DeleteMapping("/{bookReviewSeq}")
    public BaseResponseDto delete(@PathVariable Long bookReviewSeq, Authentication authentication) {
        log.debug("BookReviewController.delete() 책 리뷰 삭제 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            bookReviewService.deleteBookReview(bookReviewSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "리뷰를 삭제했습니다.");
        } catch (NotFoundBookReviewException e) {
            log.error("책 리뷰를 찾지 못했습니다.");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotEqualMemberException e) {
            log.error("책 리뷰 작성자가 다릅니다.");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("서버 에러 발생");

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}

