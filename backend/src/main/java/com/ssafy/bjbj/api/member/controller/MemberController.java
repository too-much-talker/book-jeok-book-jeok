package com.ssafy.bjbj.api.member.controller;

import com.ssafy.bjbj.api.member.dto.LoginDto;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody RequestMemberDto memberDto, Errors errors) {
        log.debug("MemberController.register");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String password = memberDto.getPassword();

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
        } else if (password.contains(memberDto.getEmail().split("@")[0])) {
            // 패스워드에 이메일이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "email into password");
            responseData.put("msg", "패스워드에 이메일이 포함될 수 없습니다.");
        } else if (password.contains(memberDto.getName())) {
            // 패스워드에 이름이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "name into password");
            responseData.put("msg", "패스워드에 이름이 포함될 수 없습니다.");
        } else if (password.contains(memberDto.getNickname())) {
            // 패스워드에 닉네임이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "nickname into password");
            responseData.put("msg", "패스워드에 닉네임이 포함될 수 없습니다.");
        } else if (memberService.hasEmail(memberDto.getEmail())) {
            // 이메일 중복
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 존재하는 이메일입니다.");
        } else if (memberService.hasNickname(memberDto.getNickname())) {
            // 닉네임 중복
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 존재하는 닉네임입니다.");
        } else if (!memberService.saveMember(memberDto)) {
            // 회원가입 실패
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "회원가입 실패");
        } else {
            // 회원가입 성공
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "회원가입 성공");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
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

        RequestMemberDto targetMemberDto = memberService.findMemberDtoByEmail(loginDto.getEmail());

        if (targetMemberDto == null) {
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "존재하지 않은 회원입니다.");
        } else if (!passwordEncoder.matches(loginDto.getPassword(), targetMemberDto.getPassword())) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "잘못된 비밀번호입니다.");
        } else {
            RequestMemberDto memberDto = memberService.findMemberDtoByEmail(loginDto.getEmail());
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

    @GetMapping("/nickname/{nickname}/exist")
    public BaseResponseDto isExistNickname(@PathVariable String nickname) {
        log.debug("닉네임 중복체크 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[가-힣]{2,8}|[a-z]{3,12}$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(nickname).matches()) {
            if (memberService.hasNickname(nickname)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "이미 존재하는 닉네임입니다.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "사용할 수 있는 닉네임입니다.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "닉네임 형식에 맞지 않습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}
