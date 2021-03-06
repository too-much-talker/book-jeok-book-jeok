package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResMyReadingGroupPageDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupListPageDto;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupReview;
import com.ssafy.bjbj.api.readinggroup.exception.AlreadyReviewedReadingGroupMemberException;
import com.ssafy.bjbj.api.readinggroup.exception.NotAcceptReviewOwnSelfException;
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
                    responseData.put("msg", "?????? ?????? ?????? ???????????? ?????????????????????.");
                    responseData.put("readingGroupSeq", readingGroupSeq);
                } catch (Exception e) {
                    log.error("?????? ?????? ??????");
                    e.printStackTrace();

                    status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                    responseData.put("msg", "????????? ????????? ??? ????????????.");
                }
            } else {
                /**
                 * ???????????? ??????????????? ????????? ??????, ????????? + 6days ????????? ??????????????? ???????????? ????????? ??????.
                 * deadline : 2021-12-31
                 * startDate : 2022-01-01
                 * endDate : 2022-01-07
                 */
                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", "???????????? ?????? ???????????????.");
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
            responseData.put("msg", "?????? ?????? ?????? ????????? ??????????????? ?????? API ?????? ??????");
            responseData.put("readingGroupDetail", resReadingGroupDetailDto);
        } catch (NotFoundReadingGroupException e) {
            log.error("?????? ?????? ????????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
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

    /**
     * ?????? ?????? ???????????? API
     *
     * @param readingGroupSeq : ?????? ?????? seq
     * @param authentication : ???????????? ????????? seq??? ???????????? ?????? ??????
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
            responseData.put("msg", "?????? ?????? ???????????? ??????");
        } catch (IllegalStateException e) {
            log.error("?????? ??????????????? ?????? ????????? ???????????????");
            e.printStackTrace();

            status = HttpStatus.CONFLICT.value();
            responseData.put("msg", e.getMessage());
        } catch (NotFoundReadingGroupException e) {
            log.error("?????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("?????? ?????? ??????");
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
     * ?????? ?????? ???????????? ?????? API
     *
     * @param readingGroupSeq : ?????? ?????? seq
     * @param memberSeq : ?????? ?????? ????????? ?????? ??? ????????? ????????? seq
     * @param authentication : ?????? ?????? memberSeq??? ???????????? ????????? seq??? ???????????? ?????? ??????
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
            responseData.put("msg", "???????????? ?????? ???????????????.");
        } else {
            try {
                readingGroupMemberService.unJoin(readingGroupSeq, realMemberSeq);

                status = HttpStatus.NO_CONTENT.value();
                responseData.put("msg", "?????? ?????? ???????????? ?????? ??????");
            } catch (NotFoundReadingGroupMemberException e) {
                log.error("?????? ?????? ?????? ??????");
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                log.error("?????? ?????? ??????");
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
     * ?????? ?????? ?????? ????????? ????????? ???????????????.
     *
     * @param pageable : ??????????????????
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
            responseData.put("msg", "?????? ?????? ?????? ????????? ?????? ?????? ??????");
            responseData.put("readingGroups", resReadingGroupListPageDto);
        } catch (Exception e) {
            log.error("?????? ?????? ??????");

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
            responseData.put("msg", "?????? ?????? ?????? ?????? ?????? ??????");
            responseData.put("isMeetToday", isMeetToday);
        } catch (NotFoundReadingGroupException e) {
            log.error("?????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (IllegalStateException e) {
            log.error("????????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotFoundReadingGroupMemberException e) {
            log.error("?????? ?????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("?????? ?????? ??????");

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
                    responseData.put("msg", "?????? ?????? ?????? ???????????? ?????????????????????.");
                    responseData.put("readingGroupSeq", readingGroupSeq);
                } catch (NotFoundReadingGroupException e) {
                    log.error("???????????? ?????? ?????? ?????? ??????");
                    e.printStackTrace();

                    status = HttpStatus.BAD_REQUEST.value();
                    responseData.put("msg", e.getMessage());
                } catch (NotEqualMemberException e) {
                    log.error("?????? ????????? ???????????? ?????? ?????? ??????");
                    e.printStackTrace();

                    status = HttpStatus.BAD_REQUEST.value();
                    responseData.put("msg", e.getMessage());
                } catch (Exception e) {
                    log.error("?????? ?????? ??????");
                    e.printStackTrace();

                    status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                    responseData.put("msg", "????????? ????????? ??? ????????????.");
                }
            } else {
                /**
                 * ???????????? ??????????????? ????????? ??????, ????????? + 6days ????????? ??????????????? ???????????? ????????? ??????.
                 * deadline : 2021-12-31
                 * startDate : 2022-01-01
                 * endDate : 2022-01-07
                 */
                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", "???????????? ?????? ???????????????.");
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
            responseData.put("msg", "?????? ????????? ?????????????????????.");
        } catch (NotFoundReadingGroupException e) {
            log.error("???????????? ?????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (NotEqualMemberException e) {
            log.error("?????? ????????? ???????????? ?????? ?????? ??????");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
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
            responseData.put("msg", "?????? ?????? ?????? ?????? ??????");
            responseData.put("totalCnt", resMyReadingGroupPageDto.getTotalCnt());
            responseData.put("currentPage", resMyReadingGroupPageDto.getCurrentPage());
            responseData.put("totalPage", resMyReadingGroupPageDto.getTotalPage());
            responseData.put("readingGroups", resMyReadingGroupPageDto.getMyReadingGroupDtos());
        }catch(Exception e) {
            // Server error : Database Connection Fail, etc..
            log.debug("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "????????? ????????? ??? ????????????.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/{readingGroupSeq}/review")
    public BaseResponseDto review(@PathVariable Long readingGroupSeq, @Valid @RequestBody Map<String, ReadingGroupReview> reviews, Errors errors, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

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
                readingGroupService.reviewReadingGroupMember(readingGroupSeq, reviews, memberSeq);

                status = HttpStatus.OK.value();
                responseData.put("msg", "?????? ??????");
            } catch (NotFoundReadingGroupMemberException e) {
                log.error("?????? ?????? ?????? ?????? ??????");
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (AlreadyReviewedReadingGroupMemberException e) {
                log.error("?????? ?????? ?????? ?????? ?????? ??????");
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (NotAcceptReviewOwnSelfException e) {
                log.error("?????? ?????? ?????? ??????");
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
