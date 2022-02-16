package com.ssafy.bjbj.api.challenge.controller;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResRewardDto;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.service.ChallengeService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping
    public BaseResponseDto list(@RequestParam boolean all, Pageable pageable) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            List<ResRewardDto> rewardDtos = challengeService.getRewardDtos();
            System.out.println("rewardDtos = " + rewardDtos);
            ResChallengeListPageDto resChallengeListPageDto = challengeService.getResChallengeListPageDto(all, pageable);

            status = HttpStatus.OK.value();;
            responseData.put("msg", "챌린지 모집 포스팅 목록 조회 성공");
            responseData.put("totalCnt", resChallengeListPageDto.getTotalCnt());
            responseData.put("currentPage", resChallengeListPageDto.getCurrentPage());
            responseData.put("totalPage", resChallengeListPageDto.getTotalPage());
            responseData.put("challengeMiniDtos", resChallengeListPageDto.getChallengeMiniDtos());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/{challengeSeq}")
    public BaseResponseDto detail(@PathVariable Long challengeSeq) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResChallengeDto resChallengeDto = challengeService.getResChallengeDto(challengeSeq);
            List<ResRewardDto> rewardDtos = challengeService.getRewardDtos();

            status = HttpStatus.OK.value();
            responseData.put("msg", "챌린지 모집 포스팅 상세페이지 조회 성공");
            responseData.put("challengeInfo", resChallengeDto);
        } catch (NotFoundChallengeException e) {
            log.error("존재하지 않는 챌린지를 조회하였습니다.");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
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
