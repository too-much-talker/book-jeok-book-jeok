package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookSearchDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookListDto;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.bookinfo.service.BookInfoService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookinfos")
@RestController
public class BookInfoController {

    private final BookInfoService bookInfoService;

    @GetMapping("/{bookInfoSeq}")
    public BaseResponseDto getDetail(@PathVariable Long bookInfoSeq) {
        log.debug("BookInfoController.getDetail() 책 정보 조회 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResBookInfoDto resBookInfoDto = bookInfoService.findResBookInfoDtoBySeq(bookInfoSeq);

            status = HttpStatus.OK.value();
            responseData.put("bookInfo", resBookInfoDto);
        } catch (NotFoundBookInfoException e) {
            log.error("책 정보를 찾지 못했습니다.");

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

    @PostMapping
    public BaseResponseDto search(@Valid @RequestBody ReqBookSearchDto reqBookSearchDto, Errors errors) {
        log.debug("BookInfoController.search() 책 정보 검색 API 호출");

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
            ResBookListDto resBookListDto = bookInfoService.findResBookSearchDto(reqBookSearchDto);

            if (resBookListDto == null) {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "검색된 책이 없습니다.");
            } else {
                status = HttpStatus.OK.value();
                responseData.put("totalCnt", resBookListDto.getTotalCnt());
                responseData.put("currentPage", resBookListDto.getCurrentPage());
                responseData.put("totalPage", resBookListDto.getTotalPage());
                responseData.put("bookList", resBookListDto.getResBookInfoSmallDtos());
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
    
}
