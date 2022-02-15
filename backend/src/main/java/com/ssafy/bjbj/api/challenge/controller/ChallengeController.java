package com.ssafy.bjbj.api.challenge.controller;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.service.ChallengeService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/challenges")
@RestController
public class ChallengeController {

    private final ChallengeService challengeService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto create(@Valid @RequestBody ReqChallengeDto reqChallengeDto, Errors errors, Authentication authentication) {
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
            String deadline = reqChallengeDto.getDeadline();
            String startDate = reqChallengeDto.getStartDate();
            String startDatePlusSixDays = LocalDate.parse(startDate).plusDays(6L).toString();
            String endDate = reqChallengeDto.getEndDate();
            if (deadline.compareTo(startDate) < 0 && startDatePlusSixDays.compareTo(endDate) <= 0) {
                Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
                try {
                    Long challengeSeq = challengeService.save(reqChallengeDto, memberSeq).getSeq();

                    status = HttpStatus.CREATED.value();
                    responseData.put("msg", "챌린지 모집 포스팅을 작성하였습니다.");
                    responseData.put("challengeSeq", challengeSeq);
                } catch (Exception e) {
                    log.error("서버 에러 발생");
                    e.printStackTrace();

                    status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                    responseData.put("msg", "요청을 수행할 수 없습니다.");
                }
            } else {
                /**
                 * 마감일이 시작일보다 빨라야 하고, 시작일 + 6days 일자가 종료일보다 빠르거나 같아야 한다.
                 * deadline : 2021-12-31
                 * startDate : 2022-01-01
                 * endDate : 2022-01-07
                 */
                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", "날짜가 올바르지 않습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}