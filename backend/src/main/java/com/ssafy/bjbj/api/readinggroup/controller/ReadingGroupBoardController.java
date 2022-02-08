package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardPageDto;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupBoardService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import com.ssafy.bjbj.common.service.file.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reading-groups/boards")
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
                responseData.put("msg", "독서모임 게시글 포스팅을 작성하였습니다.");
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

    @GetMapping("/{readingGroupArticleSeq}")
    public BaseResponseDto getDetail(@PathVariable Long readingGroupArticleSeq, Authentication authentication) {
        log.debug("ReadingGroupBoardController.getDetail() 독서모임 게시글 상세조회 API 호출");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            ResReadingGroupArticleDto resReadingGroupArticleDto = readingGroupBoardService.findReadingGroupArticleBySeq(readingGroupArticleSeq, memberSeq);

            List<String> fileInfoPaths = fileInfoService.findAllFileInfoByReadingGroupBoardSeq(readingGroupArticleSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서모임 게시글을 불러왔습니다.");
            responseData.put("readingGroupArticle", resReadingGroupArticleDto);
            responseData.put("imagePaths", fileInfoPaths);
        } catch (NotFoundReadingGroupArticleException e) {
            log.error("독서모임 게시글 조회 실패");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        } catch (Exception e) {
            log.error("[Error] Exception error");
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/list/{readingGroupSeq}")
    public BaseResponseDto getList(Pageable pageable, @PathVariable Long readingGroupSeq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResReadingGroupBoardPageDto resReadingGroupBoardPageDto = readingGroupBoardService.getResReadingGroupBoardListDto(readingGroupSeq, pageable);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서 모임 게시판 목록 조회 성공");
            responseData.put("totalCnt", resReadingGroupBoardPageDto.getTotalCnt());
            responseData.put("currentPage", resReadingGroupBoardPageDto.getCurrentPage());
            responseData.put("totalPage", resReadingGroupBoardPageDto.getTotalPage());
            responseData.put("readingGroupBoards", resReadingGroupBoardPageDto.getResReadingGroupArticleDtos());
        } catch (Exception e) {
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

    @DeleteMapping("/{readingGroupArticleSeq}")
    public BaseResponseDto deleteArticle(@PathVariable Long readingGroupArticleSeq, Authentication authentication) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            readingGroupBoardService.deleteReadingGroupArticle(readingGroupArticleSeq, memberSeq);

            fileInfoService.deleteFileInfo(readingGroupArticleSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서모임 게시글을 삭제하였습니다");
        } catch (NotFoundReadingGroupArticleException | NotEqualMemberException e) {
            log.error("삭제할 독서모임 게시글 조회 실패");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("[Error] Exception error");
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
