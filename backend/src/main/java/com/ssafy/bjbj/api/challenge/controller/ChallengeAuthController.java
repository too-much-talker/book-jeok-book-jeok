package com.ssafy.bjbj.api.challenge.controller;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeAuthDto;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.exception.AlreadyAuthenticateException;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeMemberException;
import com.ssafy.bjbj.api.challenge.service.ChallengeAuthService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/challengeauths")
@RestController
public class ChallengeAuthController {

    private final ChallengeAuthService challengeAuthService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/{challengeSeq}")
    public BaseResponseDto authenticate(@PathVariable Long challengeSeq,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                        @Valid @RequestPart(value = "reqChallengeAuth") ReqChallengeAuthDto reqChallengeAuthDto,
                                        Errors errors,
                                        Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

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
                Long challengeAuthSeq = challengeAuthService.authenticate(challengeSeq, reqChallengeAuthDto, files, memberSeq).getSeq();

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "????????? ?????? ??????????????????.");
                responseData.put("challengeAuthSeq", challengeAuthSeq);
            } catch (NotFoundChallengeException e) {
                log.error("Challenge??? ?????? ??????");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (NotFoundChallengeMemberException e) {
                log.error("ChallengeMember??? ????????????");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (AlreadyAuthenticateException e) {
                log.error("?????? ?????? ????????? ????????? ??????");

                status = HttpStatus.ACCEPTED.value();
                responseData.put("msg", e.getMessage());
            } catch (IOException e) {
                log.error("?????? ?????? ??????");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "????????? ????????? ??? ????????????.");
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/reward/{challengeSeq}")
    public BaseResponseDto getReward(@PathVariable Long challengeSeq, Authentication authentication) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            Integer rewards = challengeAuthService.getRewards(memberSeq, challengeSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "????????? ??????????????????");
            responseData.put("rewards", rewards);
        } catch (NotFoundChallengeException e) {
            log.error("???????????? ?????? ???????????? ?????????????????????.");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (AlreadyAuthenticateException e) {
            log.error("???????????? ????????????.");
            e.printStackTrace();

            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "????????? ????????? ??? ????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}
