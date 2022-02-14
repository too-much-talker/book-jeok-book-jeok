package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.*;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBooklogException;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.booklog.repository.LikeRepository;
import com.ssafy.bjbj.api.member.entity.Activity;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.ActivityRepository;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.ActivityService;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.bjbj.api.member.enums.ActivityType.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BooklogServiceImpl implements BooklogService {

    private final BooklogRepository booklogRepository;

    private final BookInfoRepository bookInfoRepository;

    private final MemberRepository memberRepository;

    private final LikeRepository likeRepository;

    private final ActivityService activityService;

    private final EntityManager em;

    @Transactional
    @Override
    public Booklog register(ReqBooklogDto reqBooklogDto) {
        BookInfo bookInfo = bookInfoRepository.findBySeq(reqBooklogDto.getBookInfoSeq());
        if (bookInfo == null) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        Member member = memberRepository.findBySeq(reqBooklogDto.getMemberSeq());

        LocalDateTime readDate = reqBooklogDto.getReadDate() == null ?
                null : LocalDateTime.parse(reqBooklogDto.getReadDate() + "T00:00:00");

        Booklog savedBooklog = booklogRepository.save(Booklog.builder()
                .title(reqBooklogDto.getTitle())
                .content(reqBooklogDto.getContent())
                .summary(reqBooklogDto.getSummary())
                .starRating(reqBooklogDto.getStarRating())
                .readDate(readDate)
                .isOpen(reqBooklogDto.getIsOpen())
                .views(0)
                .member(member)
                .bookInfo(bookInfo)
                .build());

        activityService.createNewActivity(savedBooklog.getSeq(), member, BOOKLOG_CREATE, savedBooklog.getCreatedDate());

        return savedBooklog;
    }

    @Transactional
    @Override
    public Booklog update(Long booklogSeq, ReqBooklogDto reqBooklogDto) {
        Booklog savedBooklog = booklogRepository.findBySeq(booklogSeq);
        if (!savedBooklog.getBookInfo().getSeq().equals(reqBooklogDto.getBookInfoSeq())) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        savedBooklog.changeBooklog(reqBooklogDto);

        activityService.createNewActivity(savedBooklog.getSeq(), savedBooklog.getMember(), BOOKLOG_UPDATE, savedBooklog.getLastModifiedDate());

        return savedBooklog;
    }

    @Transactional
    @Override
    public void remove(Long booklogSeq, Long memberSeq) {
        Booklog findBooklog = booklogRepository.findBySeq(booklogSeq);

        if (findBooklog == null) {
            throw new NotFoundBooklogException("올바르지 않은 요청입니다.");
        } else if (!findBooklog.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        } else {
            findBooklog.delete();
            activityService.createNewActivity(findBooklog.getSeq(), findBooklog.getMember(), BOOKLOG_DELETE, findBooklog.getLastModifiedDate());
        }
    }

    @Transactional
    @Override
    public void changeIsOpen(Long booklogSeq, Long memberSeq, boolean isOpen) {
        Booklog findBooklog = booklogRepository.findBySeq(booklogSeq);

        if (findBooklog == null) {
            throw new NotFoundBooklogException("올바르지 않은 요청입니다.");
        } else if (!findBooklog.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        } else {
            findBooklog.changeIsOpen(isOpen);
            activityService.createNewActivity(findBooklog.getSeq(), findBooklog.getMember(), BOOKLOG_DELETE, findBooklog.getLastModifiedDate());
        }
    }

    @Override
    public Booklog findBySeq(Long seq) {
        return booklogRepository.findBySeq(seq);
    }

    @Transactional
    @Override
    public ResBooklogDto getResBooklogDtoBooklog(Long booklogSeq, Long memberSeq) {
        Booklog booklog = booklogRepository.findBySeq(booklogSeq);
        if (booklog == null) {
            log.debug("Booklog is null");
            throw new NotFoundBooklogException("올바르지 않은 요청입니다.");
        }

        if (!booklog.getMember().getSeq().equals(memberSeq)) {
            if (booklog.isOpen()) {
                // 다른 회원의 북로그를 조회할 경우 조회수 증가
                booklog.incrementViews();
            } else {
                log.debug("다른 회원의 비공개 북로그 조회 시도");
                throw new IllegalArgumentException("올바르지 않은 요청입니다.");
            }
        }

        return ResBooklogDto.builder()
                .booklogSeq(booklog.getSeq())
                .memberSeq(booklog.getMember().getSeq())
                .bookInfoSeq(booklog.getBookInfo().getSeq())
                .nickname(booklog.getMember().getNickname())
                .likes(booklog.getLikes().size())
                .title(booklog.getTitle())
                .content(booklog.getContent())
                .summary(booklog.getSummary())
                .starRating(booklog.getStarRating())
                .readDate(booklog.getReadDate() == null ? null : booklog.getReadDate().toLocalDate())
                .isOpen(booklog.isOpen())
                .views(booklog.getViews())
                .createdDate(booklog.getCreatedDate().toLocalDate())
                .build();
    }

    @Override
    public ResOpenBooklogPageDto getResOpenBooklogListDto(Pageable pageable) {
        Integer totalCnt = booklogRepository.countByOpenBooklogAndRecentOneWeek();
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<OpenBooklogDto> openBooklogDtos = booklogRepository.findOpenBooklogDtos(pageable);

        return ResOpenBooklogPageDto.builder()
                .totalCnt(totalCnt)
                .currentPage(pageable.getPageNumber())
                .totalPage(totalPage)
                .openBooklogDtos(openBooklogDtos)
                .build();
    }

    @Override
    public ResMyBooklogPageDto getResMyBooklogPageDto(boolean isAll, Pageable pageable, Long memberSeq) {
        Integer totalCnt = booklogRepository.countMyBooklogByMemberSeq(isAll, memberSeq);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<MyBooklogDto> myBooklogDtos = booklogRepository.findMyBooklogDtos(isAll, pageable, memberSeq);

        return ResMyBooklogPageDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .myBooklogDtos(myBooklogDtos)
                .build();
    }

    @Override
    public ResSearchBooklogPageDto getResSearchBooklogPageDto(Pageable pageable, String keyword, String writer) {
        Integer totalCnt = booklogRepository.countSearchBooklogByKeyword(keyword, writer);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<SearchBooklogDto> searchBooklogDtos = booklogRepository.findSearchBooklog(pageable, keyword, writer);

        return ResSearchBooklogPageDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .searchBooklogDtos(searchBooklogDtos)
                .build();
    }

    @Override
    public ResLikeBooklogPageDto getResLikeBooklogPageDto(Pageable pageable, Long memberSeq) {
        Integer totalCnt = likeRepository.countByMemberSeq(memberSeq);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<LikeBooklogDto> likeBooklogDtos = booklogRepository.findLikeBooklogDtos(pageable, memberSeq);

        return ResLikeBooklogPageDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .likeBooklogDtos(likeBooklogDtos)
                .build();
    }

    @Override
    public ResOpenBooklogPageByBookInfoDto getResOpenBooklogPageByBookInfoDto(Long bookInfoSeq, Pageable pageable) {
        Integer totalCnt = booklogRepository.countOpenBooklogByBookInfoSeq(bookInfoSeq);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<OpenBooklogByBookInfoDto> openBooklogByBookInfoDtos = booklogRepository.findOpenBooklogByBookInfoDtos(bookInfoSeq, pageable);

        return ResOpenBooklogPageByBookInfoDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .openBooklogByBookInfoDtos(openBooklogByBookInfoDtos)
                .build();
    }

}
