package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardPageDto;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupBoardService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import com.ssafy.bjbj.common.service.file.FileInfoService;
import com.ssafy.bjbj.common.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/reading-groups/boards")
@RestController
public class ReadingGroupBoardController {

    private final ReadingGroupBoardService readingGroupBoardService;

    private final FileInfoService fileInfoService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(
            @Valid @RequestPart(value = "reqReadingGroupBoard") ReqReadingGroupBoardDto reqReadingGroupBoardDto,
            Errors errors,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            Authentication authentication) throws Exception {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("files = " + files);

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
                Long readingGroupBoardSeq = readingGroupBoardService.register(reqReadingGroupBoardDto, files, memberSeq);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "???????????? ????????? ?????????????????????.");
                responseData.put("readingGroupBoardSeq", readingGroupBoardSeq);
            } catch (NotFoundReadingGroupException e) {
                log.error("ReadingGroup??? ?????? ??????");

                status = HttpStatus.BAD_REQUEST.value();
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
    @GetMapping("/{readingGroupArticleSeq}")
    public BaseResponseDto getDetail(@PathVariable Long readingGroupArticleSeq, Authentication authentication) {
        log.debug("ReadingGroupBoardController.getDetail() ???????????? ????????? ???????????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            ResReadingGroupArticleDto resReadingGroupArticleDto = readingGroupBoardService.findReadingGroupArticleBySeq(readingGroupArticleSeq, memberSeq);

            List<String> fileInfoPaths = fileInfoService.findAllFileInfoByReadingGroupBoardSeq(readingGroupArticleSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "???????????? ???????????? ??????????????????.");
            responseData.put("readingGroupArticle", resReadingGroupArticleDto);
            responseData.put("imagePaths", fileInfoPaths);
        } catch (NotFoundReadingGroupArticleException e) {
            log.error("???????????? ????????? ?????? ??????");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "???????????? ?????? ???????????????.");
        } catch (Exception e) {
            log.error("[Error] Exception error");
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
    @GetMapping("/list/{readingGroupSeq}")
    public BaseResponseDto getList(Pageable pageable, @PathVariable Long readingGroupSeq) {

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResReadingGroupBoardPageDto resReadingGroupBoardPageDto = readingGroupBoardService.getResReadingGroupBoardListDto(readingGroupSeq, pageable);

            status = HttpStatus.OK.value();
            responseData.put("msg", "?????? ?????? ????????? ?????? ?????? ??????");
            responseData.put("totalCnt", resReadingGroupBoardPageDto.getTotalCnt());
            responseData.put("currentPage", resReadingGroupBoardPageDto.getCurrentPage());
            responseData.put("totalPage", resReadingGroupBoardPageDto.getTotalPage());
            responseData.put("readingGroupBoards", resReadingGroupBoardPageDto.getResReadingGroupArticleDtos());
        } catch (Exception e) {
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
    @PutMapping("/{readingGroupArticleSeq}")
    public BaseResponseDto updateArticle(@PathVariable Long readingGroupArticleSeq,
                                         @Valid @RequestPart(value = "reqReadingGroupBoard") ReqReadingGroupBoardDto reqReadingGroupBoardDto,
                                         @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         Authentication authentication) {
        log.debug("ReadingGroupBoardController.updateArticle() ???????????? ????????? ?????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResReadingGroupArticleDto resReadingGroupArticleDto = readingGroupBoardService.updateReadingGroupArticleBySeq(readingGroupArticleSeq, memberSeq, reqReadingGroupBoardDto);

            fileInfoService.update(readingGroupArticleSeq, files);
            List<String> fileInfoPaths = fileInfoService.findAllFileInfoByReadingGroupBoardSeq(readingGroupArticleSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "???????????? ???????????? ??????????????????.");
            responseData.put("readingGroupArticle", resReadingGroupArticleDto);
            responseData.put("imagePaths", fileInfoPaths);
        } catch (NotFoundReadingGroupArticleException | NotEqualMemberException | NotFoundReadingGroupException e) {
            log.error("????????? ???????????? ????????? ?????? ??????");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("[Error] Exception error");
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
    @DeleteMapping("/{readingGroupArticleSeq}")
    public BaseResponseDto deleteArticle(@PathVariable Long readingGroupArticleSeq, Authentication authentication) {
        log.debug("ReadingGroupBoardController.deleteArticle() ???????????? ????????? ?????? API ??????");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            readingGroupBoardService.deleteReadingGroupArticle(readingGroupArticleSeq, memberSeq);

            fileInfoService.delete(readingGroupArticleSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "???????????? ???????????? ?????????????????????");
        } catch (NotFoundReadingGroupArticleException | NotEqualMemberException e) {
            log.error("????????? ???????????? ????????? ?????? ??????");

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("[Error] Exception error");
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
