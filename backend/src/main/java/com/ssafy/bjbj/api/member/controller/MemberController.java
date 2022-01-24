package com.ssafy.bjbj.api.member.controller;

import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody MemberDto memberDto, Errors errors) {
        log.debug("MemberController.register");
        Map<String, Object> responseData = new HashMap<>();

        if (errors.hasErrors()) {
            if (errors.hasFieldErrors()) {
                // field error
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("msg", errors.getFieldError().getDefaultMessage());

                return BaseResponseDto.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .data(responseData)
                        .build();
            } else {
                // global error
                responseData.put("msg", "global error");
                return BaseResponseDto.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .data(responseData)
                        .build();
            }
        }

        if (memberService.hasEmail(memberDto.getEmail())) {
            responseData.put("msg", "이미 존재하는 이메일입니다.");
            return BaseResponseDto.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .data(responseData)
                    .build();
        }

        if (memberService.hasNickname(memberDto.getNickname())) {
            responseData.put("msg", "이미 존재하는 닉네임입니다.");
            return BaseResponseDto.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .data(responseData)
                    .build();
        }

        if (memberService.saveMember(memberDto)) {
            responseData.put("msg", "회원가입 성공");
            return BaseResponseDto.builder()
                    .status(HttpStatus.CREATED.value())
                    .data(responseData)
                    .build();
        } else {
            responseData.put("msg", "회원가입 실패");
            return BaseResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(responseData)
                    .build();
        }
    }

    @GetMapping("/email/{email}/exist")
    public BaseResponseDto isExistEmail(@PathVariable String email) {
        log.debug("이메일 중복체크 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[0-9a-zA-Z]+([.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+([.-]+[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]{2,3})+$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(email).matches()) {
            if (memberService.hasEmail(email)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "이미 존재하는 이메일입니다.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "사용하실 수 있는 이메일입니다.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "이메일 형식에 맞지 않습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}
