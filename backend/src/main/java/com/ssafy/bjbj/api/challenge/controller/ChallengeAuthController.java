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
                responseData.put("msg", "챌린지 인증 성공했습니다.");
                responseData.put("challengeAuthSeq", challengeAuthSeq);
            } catch (NotFoundChallengeException e) {
                log.error("Challenge가 없는 경우");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (NotFoundChallengeMemberException e) {
                log.error("ChallengeMember가 아닌경우");

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (AlreadyAuthenticateException e) {
                log.error("이미 오늘 인증한 유저일 경우");

                status = HttpStatus.ACCEPTED.value();
                responseData.put("msg", e.getMessage());
            } catch (IOException e) {
                log.error("파일 저장 실패");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", "요청을 수행할 수 없습니다.");
            } catch (Exception e) {
                log.error("서버 에러 발생");
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/reward/{challengeSeq}")
    public BaseResponseDto getReward(@PathVariable Long challengeSeq, Authentication authentication) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            Integer rewards = challengeAuthService.getRewards(memberSeq, challengeSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "지급될 리워드입니다");
            responseData.put("rewards", rewards);
        } catch (NotFoundChallengeException e) {
            log.error("존재하지 않는 챌린지를 조회하였습니다.");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (AlreadyAuthenticateException e) {
            log.error("종료일이 아닙니다.");
            e.printStackTrace();

            status = HttpStatus.ACCEPTED.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("서버 에러 발생");
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
