package com.ssafy.bjbj.api.booklog.controller;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.booklog.dto.response.ResBooklogDto;
import com.ssafy.bjbj.api.booklog.exception.DuplicateLikeException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBooklogException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundLikeException;
import com.ssafy.bjbj.api.booklog.service.BooklogService;
import com.ssafy.bjbj.api.booklog.service.LikeService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
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
@RequestMapping("/api/v1/booklogs")
@RestController
public class BooklogController {

    private final BooklogService booklogService;

    private final LikeService likeService;

    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody RequestBooklogDto requestBooklogDto, Errors errors, Authentication authentication) {
        log.debug("BooklogController.register() 북로그 작성 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        if (!memberSeq.equals(requestBooklogDto.getMemberSeq())) {
            // 다른 계정의 seq를 보냈을 때
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
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
            try {
                booklogService.register(requestBooklogDto);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "북로그를 작성하였습니다.");
            } catch (NotFoundBookInfoException e) {
                // db에 없는 책 정보 seq를 보냈을 때 (조작)

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                // Server error : Database Connection Fail, etc..
                log.debug("[Error] Exception error");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "요청을 수행할 수 없습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PutMapping("/{booklogSeq}")
    public BaseResponseDto update(@PathVariable Long booklogSeq, @Valid @RequestBody RequestBooklogDto requestBooklogDto, Errors errors, Authentication authentication) {
        log.debug("BooklogController.modify() 북로그 수정 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        if (!memberSeq.equals(requestBooklogDto.getMemberSeq())) {
            // 다른 계정의 seq를 보냈을 때
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
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
            try {
                booklogService.update(booklogSeq, requestBooklogDto);

                status = HttpStatus.OK.value();
                responseData.put("msg", "북로그를 수정하였습니다.");
            } catch (NotFoundBookInfoException e) {
                // db에 없는 책 정보 seq를 보냈을 때 (조작)

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                // Server error : Database Connection Fail, etc..
                log.debug("[Error] Exception error");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "요청을 수행할 수 없습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @DeleteMapping("/{booklogSeq}")
    public BaseResponseDto remove(@PathVariable Long booklogSeq, Authentication authentication) {
        log.debug("BooklogController.remove() 북로그 삭제 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            booklogService.remove(booklogSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "북로그를 삭제하였습니다.");
        } catch (NotFoundBooklogException | NotEqualMemberException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 북로그의 주인 member와 요청한 member가 다를 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PatchMapping("/{booklogSeq}")
    public BaseResponseDto changeIsOpen(@PathVariable Long booklogSeq, @RequestBody Boolean open, Authentication authentication) {
        log.debug("BooklogController.changeIsOpen() 북로그 공개여부 변경 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            booklogService.changeIsOpen(booklogSeq, memberSeq, open);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그의 공개여부를 수정였습니다.");
        } catch (NotFoundBooklogException | NotEqualMemberException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 북로그의 주인 member와 요청한 member가 다를 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PostMapping("/{booklogSeq}/like")
    public BaseResponseDto like(@PathVariable Long booklogSeq, Authentication authentication) {
        log.debug("BooklogController.like() 북로그 좋아요(하트) API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            likeService.like(booklogSeq, memberSeq);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "좋아요를 눌렀습니다.");
        } catch (NotFoundBooklogException | DuplicateLikeException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 이미 좋아요를 눌렀을 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
    
    @DeleteMapping("/{booklogSeq}/like")
    public BaseResponseDto unLike(@PathVariable Long booklogSeq, Authentication authentication) {
        log.debug("BooklogController.unLike() 북로그 좋아요(하트) 취소 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            likeService.unLike(booklogSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "좋아요를 취소했습니다.");
        } catch (NotFoundLikeException | NotEqualMemberException e) {
            /**
             * 1. db에 like가 없을 때
             * 2. like의 주인이 아닐 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/{booklogSeq}")
    public BaseResponseDto get(@PathVariable Long booklogSeq, Authentication authentication) {
        log.debug("BooklogController.getBooklog() 북로그 조회 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResBooklogDto resBooklogDto = booklogService.getResBooklogDtoBooklog(booklogSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그 조회 성공");
            responseData.put("booklog", resBooklogDto);
        } catch (NotFoundBooklogException | IllegalArgumentException e) {
            /**
             * 1. DB에 없는 북로그를 요청했을 때
             * 2. 다른 사람이 작성한 비공개 북로그를 요청했을 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}
