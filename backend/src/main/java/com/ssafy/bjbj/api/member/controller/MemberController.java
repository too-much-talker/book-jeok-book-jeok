package com.ssafy.bjbj.api.member.controller;

import com.ssafy.bjbj.api.member.dto.LoginDto;
import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/login")
    public BaseResponseDto login(@Valid @RequestBody LoginDto loginDto, Errors errors) {

        Integer status = null;
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

        MemberDto targetMemberDto = memberService.findMemberDtoByEmail(loginDto.getEmail());

        if (targetMemberDto == null) {
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "존재하지 않은 회원입니다.");
        } else if (!passwordEncoder.matches(loginDto.getPassword(), targetMemberDto.getPassword())) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "잘못된 비밀번호입니다.");
        } else {
            MemberDto memberDto = memberService.findMemberDtoByEmail(loginDto.getEmail());
            String jwtToken = JwtTokenUtil.getToken(memberDto.getEmail());

            status = status = HttpStatus.OK.value();
            responseData.put("message", "로그인에 성공했습니다.");
            responseData.put("memberInfo", memberDto);
            responseData.put("jwtToken", jwtToken);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}
