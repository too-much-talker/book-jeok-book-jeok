package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupBoardService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.service.file.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/readinggroupboards")
@RestController
public class ReadingGroupBoardController {

    private final ReadingGroupBoardService readingGroupBoardService;

    private final FileInfoService fileInfoService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(
            @Valid @RequestPart(value = "reqReadingGroupBoard") ReqReadingGroupBoardDto reqReadingGroupBoardDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            Authentication authentication) throws Exception {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        boolean isAuthenticatedMember = details.getMember().getSeq().equals(reqReadingGroupBoardDto.getMemberSeq());

        if (!isAuthenticatedMember) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("msg", "인증되지 않은 회원입니다");
        } else {

            try {
                Long seq = readingGroupBoardService.register(reqReadingGroupBoardDto, files);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "독서모임 포스팅을 작성하였습니다.");
                responseData.put("readingGroupBoardSeq", seq);
            } catch (Exception e) {
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

    @GetMapping("/{readingGroupBoardSeq}")
    public BaseResponseDto getDetail(@PathVariable Long readingGroupBoardSeq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        ResReadingGroupBoardDto resReadingGroupBoardDto = readingGroupBoardService.findReadingGroupBoardBySeq(readingGroupBoardSeq);

        if (resReadingGroupBoardDto == null) {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "요청한 독서모임 게시글이 없습니다.");
        } else {
            status = HttpStatus.OK.value();
            responseData.put("msg", "독서모임 게시글을 불러왔습니다.");
            responseData.put("readingGroupBoard", resReadingGroupBoardDto);
            List<String> fileInfoPaths = fileInfoService.findAllFileInfoByReadingGroupBoardSeq(readingGroupBoardSeq);
            responseData.put("imagePaths", fileInfoPaths);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }
}
