package com.ssafy.bjbj.api.challenge.controller;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.*;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeMemberException;
import com.ssafy.bjbj.api.challenge.service.ChallengeService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
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
            ResChallengeListPageDto resChallengeListPageDto = challengeService.getResChallengeListPageDto(all, pageable);

            status = HttpStatus.OK.value();
            ;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/me")
    public BaseResponseDto myChallenges(@RequestParam boolean end, Pageable pageable, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            List<ResRewardDto> rewardDtos = challengeService.getRewardDtos();
            ResMyChallengeListPageDto resMyChallengeListPageDto = challengeService.getResMyChallengeListPageDto(end, pageable, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "나의 챌린지 목록 조회 성공");
            responseData.put("totalCnt", resMyChallengeListPageDto.getTotalCnt());
            responseData.put("currentPage", resMyChallengeListPageDto.getCurrentPage());
            responseData.put("totalPage", resMyChallengeListPageDto.getTotalPage());
            responseData.put("myChallenges", resMyChallengeListPageDto.getMyChallengeDtos());
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
    @PostMapping("/{challengeSeq}/join")
    public BaseResponseDto join(@PathVariable Long challengeSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            challengeService.join(challengeSeq, memberSeq);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "챌린지 참여신청이 완료되었습니다.");
        } catch (NotFoundChallengeException e) {
            log.error("존재하지 않는 챌린지를 조회하였습니다.");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (IllegalStateException e) {
            log.error("이미 신청한 챌린지를 다시 신청하였습니다.");
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @DeleteMapping("/{challengeSeq}/join")
    public BaseResponseDto unJoin(@PathVariable Long challengeSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            challengeService.unJoin(challengeSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "챌린지 참여신청을 취소하였습니다.");
        } catch (NotFoundChallengeMemberException e) {
            log.error("신청하지 않은 챌린지를 취소 요청하였습니다.");
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')")
    @PutMapping("/{challengeSeq}")
    public BaseResponseDto update(@PathVariable Long challengeSeq,
                                  Authentication authentication,
                                  @Valid @RequestBody ReqChallengeDto reqChallengeDto,
                                  Errors errors) {
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
                    Long savedChallengeSeq = challengeService.modify(challengeSeq, memberSeq, reqChallengeDto).getSeq();

                    status = HttpStatus.OK.value();
                    responseData.put("msg", "챌린지 모집 포스팅을 수정하였습니다.");
                    responseData.put("challengeSeq", savedChallengeSeq);
                } catch (NotFoundChallengeException e) {
                    log.error("존재하지 않는 챌린지의 수정 시도");
                    e.printStackTrace();

                    status = HttpStatus.BAD_REQUEST.value();
                    responseData.put("msg", e.getMessage());
                } catch (NotEqualMemberException e) {
                    log.error("다른 회원의 챌린지 수정 시도");
                    e.printStackTrace();

                    status = HttpStatus.BAD_REQUEST.value();
                    responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')")
    @DeleteMapping("/{challengeSeq}")
    public BaseResponseDto delete(@PathVariable Long challengeSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            challengeService.remove(challengeSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "챌린지 모집 포스팅을 삭제하였습니다.");
        } catch (NotFoundChallengeException e) {
            log.error("존재하지 않는 챌린지 삭제 시도");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotEqualMemberException e) {
            log.error("다른 회원의 챌린지 삭제 시도");
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MEMBER')")
    @GetMapping("/me/{challengeSeq}")
    public BaseResponseDto myChallenge(@PathVariable Long challengeSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResMyChallengeDetailDto resMyChallengeDetailDto = challengeService.getMyChallengeDetailDto(challengeSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "나의 챌린지 상세페이지 조회 성공");
            responseData.put("myChallengeDetail", resMyChallengeDetailDto);
        } catch (NotFoundChallengeException e) {
            log.error("존재하지 않는 챌린지를 조회하였습니다.");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotFoundChallengeMemberException e) {
            log.error("참여하지 않은 챌린지를 조회하였습니다.");
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
