package com.ssafy.bjbj.api.member.controller;

import com.ssafy.bjbj.api.member.dto.request.ReqLoginMemberDto;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public BaseResponseDto register(@Valid @RequestBody ReqMemberDto reqMemberDto, Errors errors) {
        log.debug("MemberController.register");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String password = reqMemberDto.getPassword();

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
        } else if (password.contains(reqMemberDto.getEmail().split("@")[0])) {
            // 패스워드에 이메일이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "email into password");
            responseData.put("msg", "패스워드에 이메일이 포함될 수 없습니다.");
        } else if (password.contains(reqMemberDto.getName())) {
            // 패스워드에 이름이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "name into password");
            responseData.put("msg", "패스워드에 이름이 포함될 수 없습니다.");
        } else if (password.contains(reqMemberDto.getNickname())) {
            // 패스워드에 닉네임이 포함된 경우
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "nickname into password");
            responseData.put("msg", "패스워드에 닉네임이 포함될 수 없습니다.");
        } else if (memberService.hasEmail(reqMemberDto.getEmail())) {
            // 이메일 중복
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 존재하는 이메일입니다.");
        } else if (memberService.hasNickname(reqMemberDto.getNickname())) {
            // 닉네임 중복
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 존재하는 닉네임입니다.");
        } else if (memberService.register(reqMemberDto) == null) {
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
    public BaseResponseDto login(@Valid @RequestBody ReqLoginMemberDto reqLoginMemberDto, Errors errors) {
        log.debug("MemberController.login() 호출");

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
            ResLoginMemberDto resLoginMemberDto = memberService.findResLoginMemberDtoByEmail(reqLoginMemberDto.getEmail());
            if (resLoginMemberDto == null) {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "존재하지 않는 회원입니다.");
            } else if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), resLoginMemberDto.getPassword())) {
                status = HttpStatus.UNAUTHORIZED.value();
                responseData.put("msg", "잘못된 비밀번호입니다.");
            } else {
                String jwtToken = JwtTokenUtil.getToken(resLoginMemberDto.getEmail());

                status = HttpStatus.OK.value();
                responseData.put("message", "로그인에 성공했습니다.");
                responseData.put("memberInfo", resLoginMemberDto);
                responseData.put("jwtToken", jwtToken);
            }
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/subscribe/{toMemberSeq}")
    public BaseResponseDto subscribe(@PathVariable Long toMemberSeq, Authentication authentication) {
        log.debug("팔로우 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean canSubscribe = memberService.subscribeMember(details.getMember().getSeq(), toMemberSeq);

        if (canSubscribe) {
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "구독에 성공했습니다.");
        } else  {
            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", "이미 구독중인 유저입니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/unsubscribe/{toMemberSeq}")
    public BaseResponseDto unsubscribe(@PathVariable long toMemberSeq, Authentication authentication) {
        log.debug("언팔로우 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean canUnsubscribeMember = memberService.unsubscribeMember(details.getMember().getSeq(), toMemberSeq);

        if (canUnsubscribeMember) {

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "구독에 취소에 성공했습니다.");
        } else {

            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", "이미 구독중이지 않은 유저입니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/me")
    public BaseResponseDto me(Authentication authentication) {
        log.debug("MemberController.myInfo() 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        Long seq = details.getMember().getSeq();

        responseData.put("point", memberService.getPointBySeq(seq));
        responseData.put("exp", memberService.getExpBySeq(seq));
        responseData.put("activityCountByDate", memberService.getAllActivityCounts(seq));

        status = HttpStatus.OK.value();

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PutMapping
    public BaseResponseDto update(Authentication authentication, @Valid @RequestBody ReqMemberDto reqMemberDto, Errors errors) {
        log.debug("회원정보 수정");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String password = reqMemberDto.getPassword();

        // 사용자가 api 요청시 값을 조작했는지 확인
        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        Long seq = details.getMember().getSeq();

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
        } else if ((responseData = checkPassword(password, reqMemberDto)).size() != 0) {
            // 비밀번호가 형식에 맞지 않는 경우
            status = HttpStatus.BAD_REQUEST.value();
        } else if (memberService.hasNickname(reqMemberDto.getNickname()) && memberService.findMemberByNickname(reqMemberDto.getNickname()).getSeq() != seq) {
            // 기존에 존재하는 닉네임이 내 닉네임이 아닐 경우 == 새로 변경한 닉네임이 이미 사용중인 경우(다른 사람의 닉네임)
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "이미 존재하는 닉네임입니다.");
        } else if (!memberService.updateMember(reqMemberDto, seq)) {
            // 정보 수정 실패
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "정보 수정 실패");
        } else {
            // 정보 수정 성공
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "정보 수정 성공");
        }
        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/phone/{phoneNumber}/exist")
    public BaseResponseDto isExistPhoneNumber(@PathVariable String phoneNumber) {
        log.debug("MemberController.isExistPhoneNumber() 핸드폰 번호 중복 확인 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[0-9]{3}-[0-9]{3,4}-[0-9]{3,4}$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(phoneNumber).matches()) {
            if (memberService.hasPhoneNumber(phoneNumber)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "이미 존재하는 핸드폰 번호입니다.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "사용하실 수 있는 핸드폰 번호입니다.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    
    private Map<String, Object> checkPassword(String password, ReqMemberDto reqMemberDto) {

        Map<String, Object> responseData = new HashMap<>();
        if (password.contains(reqMemberDto.getEmail().split("@")[0])) {
            // 패스워드에 이메일이 포함된 경우
            responseData.put("field", "email into password");
            responseData.put("msg", "패스워드에 이메일이 포함될 수 없습니다.");
        } else if (password.contains(reqMemberDto.getName())) {
            // 패스워드에 이름이 포함된 경우
            responseData.put("field", "name into password");
            responseData.put("msg", "패스워드에 이름이 포함될 수 없습니다.");
        } else if (password.contains(reqMemberDto.getNickname())) {
            // 패스워드에 닉네임이 포함된 경우
            responseData.put("field", "nickname into password");
            responseData.put("msg", "패스워드에 닉네임이 포함될 수 없습니다.");
        }

        return responseData;
    }

}