package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
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
@RequestMapping("/api/v1/reading-groups")
@RestController
public class ReadingGroupController {

    private final ReadingGroupService readingGroupService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqReadingGroupDto reqReadingGroupDto, Errors errors, Authentication authentication) {
        log.debug("ReadingGroupController.register() 독서 모임 모집 포스팅 작성 API 호출");

        System.out.println("reqReadingGroupDto = " + reqReadingGroupDto);

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


}
