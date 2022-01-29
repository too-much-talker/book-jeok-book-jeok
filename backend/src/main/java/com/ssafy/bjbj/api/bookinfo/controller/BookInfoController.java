package com.ssafy.bjbj.api.bookinfo.controller;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.service.BookInfoService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookinfos")
@RestController
public class BookInfoController {

    private final BookInfoService bookInfoService;

    @GetMapping("/{seq}")
    public BaseResponseDto bookInfoDetail(@PathVariable Long seq) {
        log.debug("책 상세 페이지 조회");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        ResponseBookInfoDto responseBookInfoDto = bookInfoService.findResponseBookInfoDtoBySeq(seq);

        if (responseBookInfoDto == null) {
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "존재하지 않는 책입니다.");
        } else {
            status = HttpStatus.OK.value();
            responseData.put("bookInfo", responseBookInfoDto);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}
