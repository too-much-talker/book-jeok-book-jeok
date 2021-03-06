package com.ssafy.bjbj.api.booklog.controller;

import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.booklog.dto.response.*;
import com.ssafy.bjbj.api.booklog.exception.DuplicateLikeException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBooklogException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundLikeException;
import com.ssafy.bjbj.api.booklog.service.BooklogService;
import com.ssafy.bjbj.api.booklog.service.LikeService;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/booklogs")
@RestController
public class BooklogController {

    private final BooklogService booklogService;

    private final LikeService likeService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqBooklogDto reqBooklogDto, Errors errors, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        if (!memberSeq.equals(reqBooklogDto.getMemberSeq())) {
            // 다른 계정의 seq를 보냈을 때
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        } else if (errors.hasErrors()) {
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
                booklogService.register(reqBooklogDto);

                status = HttpStatus.CREATED.value();
                responseData.put("msg", "북로그를 작성하였습니다.");
            } catch (NotFoundBookInfoException e) {
                // db에 없는 책 정보 seq를 보냈을 때 (조작)

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                // Server error : Database Connection Fail, etc..
                log.debug("[Error] Exception error");
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
    @PutMapping("/{booklogSeq}")
    public BaseResponseDto update(@PathVariable Long booklogSeq, @Valid @RequestBody ReqBooklogDto reqBooklogDto, Errors errors, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        if (!memberSeq.equals(reqBooklogDto.getMemberSeq())) {
            // 다른 계정의 seq를 보냈을 때
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        } else if (errors.hasErrors()) {
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
                booklogService.update(booklogSeq, reqBooklogDto);

                status = HttpStatus.OK.value();
                responseData.put("msg", "북로그를 수정하였습니다.");
            } catch (NotFoundBookInfoException e) {
                // db에 없는 책 정보 seq를 보냈을 때 (조작)

                status = HttpStatus.BAD_REQUEST.value();
                responseData.put("msg", e.getMessage());
            } catch (Exception e) {
                // Server error : Database Connection Fail, etc..
                log.debug("[Error] Exception error");
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
    @DeleteMapping("/{booklogSeq}")
    public BaseResponseDto remove(@PathVariable Long booklogSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            booklogService.remove(booklogSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "북로그를 삭제하였습니다.");
        } catch (NotFoundBooklogException | NotEqualMemberException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 북로그의 주인 member와 요청한 member가 다를 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PatchMapping("/{booklogSeq}")
    public BaseResponseDto changeIsOpen(@PathVariable Long booklogSeq, @RequestBody Boolean open, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            booklogService.changeIsOpen(booklogSeq, memberSeq, open);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그의 공개여부를 수정였습니다.");
        } catch (NotFoundBooklogException | NotEqualMemberException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 북로그의 주인 member와 요청한 member가 다를 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @PostMapping("/{booklogSeq}/like")
    public BaseResponseDto like(@PathVariable Long booklogSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            likeService.like(booklogSeq, memberSeq);

            status = HttpStatus.CREATED.value();
            responseData.put("msg", "좋아요를 눌렀습니다.");
        } catch (NotFoundBooklogException | DuplicateLikeException e) {
            /**
             * 1. db에 없는 북로그 seq를 보냈을 때
             * 2. 이미 좋아요를 눌렀을 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @DeleteMapping("/{booklogSeq}/like")
    public BaseResponseDto unLike(@PathVariable Long booklogSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            likeService.unLike(booklogSeq, memberSeq);

            status = HttpStatus.NO_CONTENT.value();
            responseData.put("msg", "좋아요를 취소했습니다.");
        } catch (NotFoundLikeException | NotEqualMemberException e) {
            /**
             * 1. db에 like가 없을 때
             * 2. like의 주인이 아닐 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/{booklogSeq}")
    public BaseResponseDto get(@PathVariable Long booklogSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResBooklogDto resBooklogDto = booklogService.getResBooklogDtoBooklog(booklogSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그 조회 성공");
            responseData.put("booklog", resBooklogDto);
        } catch (NotFoundBooklogException | IllegalArgumentException e) {
            /**
             * 1. DB에 없는 북로그를 요청했을 때
             * 2. 다른 사람이 작성한 비공개 북로그를 요청했을 때
             */

            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", e.getMessage());
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

    @GetMapping
    public BaseResponseDto list(Pageable pageable) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResOpenBooklogPageDto resOpenBooklogListDto = booklogService.getResOpenBooklogListDto(pageable);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그 조회 성공");
            responseData.put("totalCnt", resOpenBooklogListDto.getTotalCnt());
            responseData.put("currentPage", resOpenBooklogListDto.getCurrentPage());
            responseData.put("totalPage", resOpenBooklogListDto.getTotalPage());
            responseData.put("booklogs", resOpenBooklogListDto.getOpenBooklogDtos());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/me")
    public BaseResponseDto myBooklogList(@RequestParam boolean all, Pageable pageable, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResMyBooklogPageDto resMyBooklogPageDto = booklogService.getResMyBooklogPageDto(all, pageable, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "나의 북로그 조회 성공");
            responseData.put("totalCnt", resMyBooklogPageDto.getTotalCnt());
            responseData.put("currentPage", resMyBooklogPageDto.getCurrentPage());
            responseData.put("totalPage", resMyBooklogPageDto.getTotalPage());
            responseData.put("booklogs", resMyBooklogPageDto.getMyBooklogDtos());
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

    // /api/v1/booklogs/search?page1&size=10&keyword=북로그&writer=member1
    @GetMapping("/search")
    public BaseResponseDto search(Pageable pageable,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) String writer) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        if (
                keyword == null && writer == null ||
                        "".equals(keyword) || "".equals(writer)
        ) {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("msg", "올바르지 않은 요청입니다.");
        } else {
            try {
                ResSearchBooklogPageDto resSearchBooklogPageDto = booklogService.getResSearchBooklogPageDto(pageable, keyword, writer);

                status = HttpStatus.OK.value();
                responseData.put("msg", "북로그 검색 성공");
                responseData.put("totalCnt", resSearchBooklogPageDto.getTotalCnt());
                responseData.put("currentPage", resSearchBooklogPageDto.getCurrentPage());
                responseData.put("totalPage", resSearchBooklogPageDto.getTotalPage());
                responseData.put("booklogs", resSearchBooklogPageDto.getSearchBooklogDtos());
            } catch (Exception e) {
                // Server error : Database Connection Fail, etc..
                log.debug("[Error] Exception error");
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

    // /api/v1/booklogs/likes
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/likes")
    public BaseResponseDto likeBooklogList(Pageable pageable, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            ResLikeBooklogPageDto resLikeBooklogPageDto = booklogService.getResLikeBooklogPageDto(pageable, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "내가 좋아요한 북로그 조회 성공");
            responseData.put("totalCnt", resLikeBooklogPageDto.getTotalCnt());
            responseData.put("currentPage", resLikeBooklogPageDto.getCurrentPage());
            responseData.put("totalPage", resLikeBooklogPageDto.getTotalPage());
            responseData.put("booklogs", resLikeBooklogPageDto.getLikeBooklogDtos());
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MEMBER')")
    @GetMapping("/{booklogSeq}/like")
    public BaseResponseDto isLike(@PathVariable Long booklogSeq, Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        Long memberSeq = ((CustomUserDetails) authentication.getDetails()).getMember().getSeq();
        try {
            boolean isLike = likeService.isLike(booklogSeq, memberSeq);

            status = HttpStatus.OK.value();
            responseData.put("msg", "북로그 좋아요 조회 성공");
            responseData.put("isLike", isLike);
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

    @GetMapping("/bookinfos/{bookInfoSeq}")
    public BaseResponseDto listByBookInfo(@PathVariable Long bookInfoSeq, Pageable pageable) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        try {
            ResOpenBooklogPageByBookInfoDto resOpenBooklogPageByBookInfoDto = booklogService.getResOpenBooklogPageByBookInfoDto(bookInfoSeq, pageable);

            status = HttpStatus.OK.value();
            responseData.put("msg", "책으로 북로그 목록 조회 성공");
            responseData.put("totalCnt", resOpenBooklogPageByBookInfoDto.getTotalCnt());
            responseData.put("currentPage", resOpenBooklogPageByBookInfoDto.getCurrentPage());
            responseData.put("totalPage", resOpenBooklogPageByBookInfoDto.getTotalPage());
            responseData.put("booklogs", resOpenBooklogPageByBookInfoDto.getOpenBooklogByBookInfoDtos());
        } catch (Exception e) {
            // Server error : Database Connection Fail, etc..
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
