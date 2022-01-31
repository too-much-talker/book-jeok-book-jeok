package com.ssafy.bjbj.api.booklog.controller;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.booklog.service.BooklogService;
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
@RequestMapping("/api/v1/booklogs")
@RestController
public class BooklogController {
    
    private final BooklogService booklogService;

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
    
}
