package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResMyReadingGroupPageDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupListPageDto;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupMemberException;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupMemberService;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupService;
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
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reading-groups")
@RestController
public class ReadingGroupController {

    private final ReadingGroupService readingGroupService;

    private final ReadingGroupMemberService readingGroupMemberService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqReadingGroupDto reqReadingGroupDto, Errors errors, Authentication authentication) {
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
            String deadline = reqReadingGroupDto.getDeadline();
            String startDate = reqReadingGroupDto.getStartDate();
            String startDatePlusSixDays = LocalDate.parse(startDate).plusDays(6L).toString();
            String endDate = reqReadingGroupDto.getEndDate();
            if (deadline.compareTo(startDate) < 0 && startDatePlusSixDays.compareTo(endDate) <= 0) {
                Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
                try {
                    Long readingGroupSeq = readingGroupService.register(reqReadingGroupDto, memberSeq).getSeq();

                    status = HttpStatus.CREATED.value();
                    responseData.put("msg", "독서 모임 모집 포스팅을 작성하였습니다.");
                    responseData.put("readingGroupSeq", readingGroupSeq);
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
                responseData.put("msg", "올바르지 않은 요청입니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/{readingGroupSeq}")
    public BaseResponseDto getDetail(@PathVariable Long readingGroupSeq) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResReadingGroupDetailDto resReadingGroupDetailDto = readingGroupService.getResReadingGroupDetailDto(readingGroupSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서 모임 모집 포스팅 상세페이지 조회 API 호출 성공");
            responseData.put("readingGroupDetail", resReadingGroupDetailDto);
        } catch (NotFoundReadingGroupException e) {
            log.error("독서 모임 엔티티 조회 실패");
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

    /**
     * 독서 모임 참여신청 API
     *
     * @param readingGroupSeq : 독서 모임 seq
     * @param authentication : 로그인한 회원의 seq를 가져오기 위해 사용
     * @return {
     *     201 -> msg
     *     400 -> msg
     *     409 -> msg
     *     500 -> msg
     * }
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/{readingGroupSeq}/members")
    public BaseResponseDto join(@PathVariable Long readingGroupSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long realMemberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            readingGroupMemberService.join(readingGroupSeq, realMemberSeq);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "독서 모임 참여신청 완료");
        } catch (IllegalStateException e) {
            log.error("이미 참여신청한 독서 모임에 재참여신청");
            e.printStackTrace();

            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", e.getMessage());
        } catch (NotFoundReadingGroupException e) {
            log.error("독서 모임 조회 실패");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("서버 장애 발생");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", e.getMessage());
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    /**
     * 독서 모임 참여신청 취소 API
     *
     * @param readingGroupSeq : 독서 모임 seq
     * @param memberSeq : 독서 모임 신청한 회원 중 취소할 회원의 seq
     * @param authentication : 전달 받은 memberSeq와 로그인한 회원의 seq를 비교하기 위해 사용
     * @return {
     *     204 -> msg
     *     400 -> msg
     *     500 -> msg
     * }
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @DeleteMapping("/{readingGroupSeq}/members/{memberSeq}")
    public BaseResponseDto unJoin(@PathVariable Long readingGroupSeq, @PathVariable Long memberSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long realMemberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        if (!realMemberSeq.equals(memberSeq)) {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        } else {
            try {
                readingGroupMemberService.unJoin(readingGroupSeq, realMemberSeq);

                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "독서 모임 참여신청 취소 완료");
            } catch (NotFoundReadingGroupMemberException e) {
                log.error("독서 모임 조회 실패");
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                log.error("서버 장애 발생");
                e.printStackTrace();

                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("msg", e.getMessage());
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    /**
     * 독서 모임 목록 페이지 정보를 반환합니다.
     *
     * @param pageable : 페이지네이션
     * @return ResReadingGroupListPageDto
     */
    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER', 'ROLE_ADMIN')")
    @GetMapping
    public BaseResponseDto list(Pageable pageable) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            readingGroupService.changeReadingGroupStatus();
            ResReadingGroupListPageDto resReadingGroupListPageDto = readingGroupService.getReadingGroupListPageDto(pageable);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서 모임 모집 포스팅 목록 조회 성공");
            responseData.put("readingGroups", resReadingGroupListPageDto);
        } catch (Exception e) {
            log.error("서버 장애 발생");

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", e.getMessage());
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER', 'ROLE_ADMIN')")
    @GetMapping("/{readingGroupSeq}/isMeetToday")
    public BaseResponseDto isMeetToday(@PathVariable Long readingGroupSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            boolean isMeetToday = readingGroupService.isMeetToday(readingGroupSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서 모임 진행 여부 확인 성공");
            responseData.put("isMeetToday", isMeetToday);
        } catch (NotFoundReadingGroupException e) {
            log.error("독서 모임 조회 실패");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (IllegalStateException e) {
            log.error("종료된 독서 모임 조회");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotFoundReadingGroupMemberException e) {
            log.error("독서 모임 회원 조회 실패");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("서버 장애 발생");

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", e.getMessage());
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER', 'ROLE_ADMIN')")
    @PutMapping("/{readingGroupSeq}")
    public BaseResponseDto modify(@PathVariable Long readingGroupSeq, @Valid @RequestBody ReqReadingGroupDto reqReadingGroupDto, Errors errors, Authentication authentication) {
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
            String deadline = reqReadingGroupDto.getDeadline();
            String startDate = reqReadingGroupDto.getStartDate();
            String startDatePlusSixDays = LocalDate.parse(startDate).plusDays(6L).toString();
            String endDate = reqReadingGroupDto.getEndDate();
            if (deadline.compareTo(startDate) < 0 && startDatePlusSixDays.compareTo(endDate) <= 0) {
                Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
                try {
                    readingGroupService.modify(readingGroupSeq, reqReadingGroupDto, memberSeq);

                    status = HttpStatus.OK.value();
                    responseData.put("msg", "독서 모임 모집 포스팅을 수정하였습니다.");
                    responseData.put("readingGroupSeq", readingGroupSeq);
                } catch (NotFoundReadingGroupException e) {
                    log.error("존재하지 않은 독서 모임 조회");
                    e.printStackTrace();

                    status = HttpStatus.BAD_REQUEST.value();
                    responseData.put("msg", e.getMessage());
                } catch (NotEqualMemberException e) {
                    log.error("독서 모임의 개설자가 아닌 자가 요청");
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
                responseData.put("msg", "올바르지 않은 요청입니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER', 'ROLE_ADMIN')")
    @DeleteMapping("/{readingGroupSeq}")
    public BaseResponseDto deleteOne(@PathVariable Long readingGroupSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            readingGroupService.removeOne(readingGroupSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "독서 모임을 삭제하였습니다.");
        } catch (NotFoundReadingGroupException e) {
            log.error("존재하지 않은 독서 모임 조회");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotEqualMemberException e) {
            log.error("독서 모임의 개설자가 아닌 자가 요청");
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
    public BaseResponseDto myReadingGroupList(Pageable pageable, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status =null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try{
            readingGroupService.changeReadingGroupStatus();
            ResMyReadingGroupPageDto resMyReadingGroupPageDto = readingGroupService.getResMyReadingGroupPageDto(pageable, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "나의 독서 모임 조회 성공");
            responseData.put("totalCnt", resMyReadingGroupPageDto.getTotalCnt());
            responseData.put("currentPage", resMyReadingGroupPageDto.getCurrentPage());
            responseData.put("totalPage", resMyReadingGroupPageDto.getTotalPage());
            responseData.put("readingGroups", resMyReadingGroupPageDto.getMyReadingGroupDtos());
        }catch(Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
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
