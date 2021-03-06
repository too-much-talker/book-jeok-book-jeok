package com.ssafy.bjbj.api.member.controller;

import com.ssafy.bjbj.api.member.dto.request.ReqLoginMemberDto;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.api.readinggroup.exception.NotAcceptResignException;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
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
            // ??????????????? ???????????? ????????? ??????
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "email into password");
            responseData.put("msg", "??????????????? ???????????? ????????? ??? ????????????.");
        } else if (password.contains(reqMemberDto.getName())) {
            // ??????????????? ????????? ????????? ??????
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "name into password");
            responseData.put("msg", "??????????????? ????????? ????????? ??? ????????????.");
        } else if (password.contains(reqMemberDto.getNickname())) {
            // ??????????????? ???????????? ????????? ??????
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("field", "nickname into password");
            responseData.put("msg", "??????????????? ???????????? ????????? ??? ????????????.");
        } else if (memberService.hasEmail(reqMemberDto.getEmail())) {
            // ????????? ??????
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "?????? ???????????? ??????????????????.");
        } else if (memberService.hasNickname(reqMemberDto.getNickname())) {
            // ????????? ??????
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "?????? ???????????? ??????????????????.");
        } else if (memberService.hasPhoneNumber(reqMemberDto.getPhoneNumber())) {
            // ????????? ?????? ??????
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "?????? ???????????? ????????? ???????????????.");
        } else {
            try {
                memberService.register(reqMemberDto);

                // ???????????? ??????
                status = HttpStatus.CREATED.value();
                responseData.put("msg", "???????????? ??????");
            } catch (Exception e) {
                log.error("???????????? ??????");

                // ???????????? ??????
                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "???????????? ??????");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PostMapping("/login")
    public BaseResponseDto login(@Valid @RequestBody ReqLoginMemberDto reqLoginMemberDto, Errors errors) {
        log.debug("MemberController.login() ??????");

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
                responseData.put("msg", "???????????? ?????? ???????????????.");
            } else if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), resLoginMemberDto.getPassword())) {
                status = HttpStatus.UNAUTHORIZED.value();
                responseData.put("msg", "????????? ?????????????????????.");
            } else {
                String jwtToken = JwtTokenUtil.getToken(resLoginMemberDto.getEmail());

                status = HttpStatus.OK.value();
                responseData.put("message", "???????????? ??????????????????.");
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
        log.debug("????????? ???????????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[0-9a-zA-Z]+([.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+([.-]+[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]{2,3})+$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(email).matches()) {
            if (memberService.hasEmail(email)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "?????? ???????????? ??????????????????.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "???????????? ??? ?????? ??????????????????.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "????????? ????????? ?????? ????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/nickname/{nickname}/exist")
    public BaseResponseDto isExistNickname(@PathVariable String nickname) {
        log.debug("????????? ???????????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[???-???]{2,8}|[a-z]{3,12}$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(nickname).matches()) {
            if (memberService.hasNickname(nickname)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "?????? ???????????? ??????????????????.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "????????? ??? ?????? ??????????????????.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "????????? ????????? ?????? ????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/subscribe/{toMemberSeq}")
    public BaseResponseDto subscribe(@PathVariable Long toMemberSeq, Authentication authentication) {
        log.debug("????????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean canSubscribe = memberService.subscribeMember(details.getMember().getSeq(), toMemberSeq);

        if (canSubscribe) {
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "????????? ??????????????????.");
        } else  {
            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", "?????? ???????????? ???????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/unsubscribe/{toMemberSeq}")
    public BaseResponseDto unsubscribe(@PathVariable long toMemberSeq, Authentication authentication) {
        log.debug("???????????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean canUnsubscribeMember = memberService.unsubscribeMember(details.getMember().getSeq(), toMemberSeq);

        if (canUnsubscribeMember) {

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "????????? ????????? ??????????????????.");
        } else {

            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", "?????? ??????????????? ?????? ???????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/me")
    public BaseResponseDto me(Authentication authentication) {
        log.debug("MemberController.myInfo() ??????");

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
        log.debug("???????????? ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String password = reqMemberDto.getPassword();

        // ???????????? api ????????? ?????? ??????????????? ??????
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
            // ??????????????? ????????? ?????? ?????? ??????
            status = HttpStatus.BAD_REQUEST.value();
        } else if (memberService.hasNickname(reqMemberDto.getNickname()) && memberService.findMemberByNickname(reqMemberDto.getNickname()).getSeq() != seq) {
            // ????????? ???????????? ???????????? ??? ???????????? ?????? ?????? == ?????? ????????? ???????????? ?????? ???????????? ??????(?????? ????????? ?????????)
            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", "?????? ???????????? ??????????????????.");
        } else if (!memberService.updateMember(reqMemberDto, seq)) {
            // ?????? ?????? ??????
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "?????? ?????? ??????");
        } else {
            // ?????? ?????? ??????
            status = HttpStatus.CREATED.value();
            responseData.put("msg", "?????? ?????? ??????");
        }
        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/phone/{phoneNumber}/exist")
    public BaseResponseDto isExistPhoneNumber(@PathVariable String phoneNumber) {
        log.debug("MemberController.isExistPhoneNumber() ????????? ?????? ?????? ?????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String regx = "^[0-9]{3}-[0-9]{3,4}-[0-9]{3,4}$";
        Pattern pattern = Pattern.compile(regx);

        if (pattern.matcher(phoneNumber).matches()) {
            if (memberService.hasPhoneNumber(phoneNumber)) {
                status = HttpStatus.OK.value();
                responseData.put("msg", "?????? ???????????? ????????? ???????????????.");
            } else {
                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "???????????? ??? ?????? ????????? ???????????????.");
            }
        } else {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "???????????? ?????? ???????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    
    private Map<String, Object> checkPassword(String password, ReqMemberDto reqMemberDto) {

        Map<String, Object> responseData = new HashMap<>();
        if (password.contains(reqMemberDto.getEmail().split("@")[0])) {
            // ??????????????? ???????????? ????????? ??????
            responseData.put("field", "email into password");
            responseData.put("msg", "??????????????? ???????????? ????????? ??? ????????????.");
        } else if (password.contains(reqMemberDto.getName())) {
            // ??????????????? ????????? ????????? ??????
            responseData.put("field", "name into password");
            responseData.put("msg", "??????????????? ????????? ????????? ??? ????????????.");
        } else if (password.contains(reqMemberDto.getNickname())) {
            // ??????????????? ???????????? ????????? ??????
            responseData.put("field", "nickname into password");
            responseData.put("msg", "??????????????? ???????????? ????????? ??? ????????????.");
        }

        return responseData;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @DeleteMapping("/resign")
    public BaseResponseDto resign(@Valid @RequestBody ReqLoginMemberDto reqLoginMemberDto, Errors errors, Authentication authentication) {
        log.debug("MemberController.resign() ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

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
            try {
                memberService.resignMember(reqLoginMemberDto, memberSeq);

                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "???????????? ??????????????????.");
            } catch (NotAcceptResignException e) {
                e.printStackTrace();

                status = HttpStatus.ACCEPTED.value();
                responseData.put("msg", "????????? ??????????????? ????????? ?????????.");
            } catch (NotEqualMemberException e) {
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                log.error("?????? ?????? ??????");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "????????? ????????? ??? ????????????.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}