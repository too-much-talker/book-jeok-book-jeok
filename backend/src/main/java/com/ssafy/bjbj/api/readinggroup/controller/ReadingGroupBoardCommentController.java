package com.ssafy.bjbj.api.readinggroup.controller;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupBoardCommentException;
import com.ssafy.bjbj.api.readinggroup.service.ReadingGroupBoardCommentService;
import com.ssafy.bjbj.common.auth.CustomUserDetails;
import com.ssafy.bjbj.common.dto.BaseResponseDto;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/reading-groups/comments")
@RestController
public class ReadingGroupBoardCommentController {

    private final ReadingGroupBoardCommentService readingGroupBoardCommentService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Errors errors, Authentication authentication) {
        log.debug("ReadingGroupBoardComment.register() 독서모임 게시판 댓글 작성 API");

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
                Long registerCommentSeq = readingGroupBoardCommentService.registerComment(reqReadingGroupBoardCommentDto, memberSeq);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "댓글을 작성하였습니다.");
                responseData.put("readingGroupCommentSeq", registerCommentSeq);
            } catch (NotFoundReadingGroupArticleException e) {
                log.error("해당 게시글 조회 실패");
                e.printStackTrace();

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/list/{readingGroupArticleSeq}")
    public BaseResponseDto getListComments(@PathVariable Long readingGroupArticleSeq) {
        log.debug("ReadingGroupBoardComment.getListComments() 독서모임 게시판 댓글 목록 조회 API");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            Integer commentCount = readingGroupBoardCommentService.countReadingGroupBoardComments(readingGroupArticleSeq);
            List<ResReadingGroupBoardCommentDto> readingGroupBoardCommentDtos = readingGroupBoardCommentService.findReadingGroupBoardCommentDtos(readingGroupArticleSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서모임 게시글 댓글 목록을 불러왔습니다.");
            responseData.put("commentCount", commentCount);
            responseData.put("comments", readingGroupBoardCommentDtos);
        } catch (NotFoundReadingGroupArticleException e) {
            log.error("해당 게시글 조회 실패");
            e.printStackTrace();

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseData.put("msg", "요청을 수행할 수 없습니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @DeleteMapping("/{readingGroupBoardCommentSeq}")
    public BaseResponseDto deleteComment(@PathVariable Long readingGroupBoardCommentSeq, Authentication authentication) {
        log.debug("ReadingGroupBoardComment.getListComments() 독서모임 게시판 댓글 삭제 API");

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();

        try {
            readingGroupBoardCommentService.deleteReadingGroupBoardComment(readingGroupBoardCommentSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "독서모임 게시판 댓글을 삭제하였습니다.");
        } catch (NotFoundReadingGroupBoardCommentException | NotEqualMemberException e) {
            log.error("삭제할 독서모임 게시글 조회 실패 or 작성자와 유저가 다른 회원");

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
