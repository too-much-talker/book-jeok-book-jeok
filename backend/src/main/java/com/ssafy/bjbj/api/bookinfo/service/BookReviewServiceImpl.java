package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookReviewException;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.ActivityService;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.bjbj.api.member.enums.ActivityType.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    private final BookInfoRepository bookInfoRepository;

    private final MemberRepository memberRepository;

    private final ActivityService activityService;

    @Override
    public BookReview findBookReviewByBookSeq(Long bookReviewSeq) {
        return bookReviewRepository.findBySeq(bookReviewSeq);
    }

    @Override
    public List<ResBookReviewByMemberDto> findAllBookReviewsByMemberSeq(Long memberSeq) {
        return bookReviewRepository.findAllBookReviewDtoByMemberSeq(memberSeq);
    }

    @Override
    public List<ResBookReviewByBookInfoDto> findAllBookReviewsByBookInfoSeq(Long bookInfoSeq) {
        return bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq);
    }

    @Transactional
    @Override
    public void deleteBookReview(Long bookReviewSeq, Long memberSeq) {
        BookReview bookReview = bookReviewRepository.findBySeq(bookReviewSeq);
        if (bookReview == null) {
            throw new NotFoundBookReviewException("올바르지 않은 요청입니다.");
        } else if (!bookReview.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        }

        bookReview.delete();
        activityService.createNewActivity(bookReview.getSeq(), bookReview.getMember(), BOOK_REVIEW_DELETE, bookReview.getLastModifiedDate());
    }

    @Transactional
    @Override
    public ResBookReviewByMemberDto registerBookReview(ReqBookReviewDto bookReviewDto, Long memberSeq) {
        Long bookInfoSeq = bookReviewDto.getBookInfoSeq();
        BookInfo bookInfo = bookInfoRepository.findBySeq(bookInfoSeq);
        if (bookInfo == null) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        BookReview latestBookReview = bookReviewRepository.findLatestBookReviewByBookInfoAndMember(bookInfoSeq, memberSeq);
        if (latestBookReview != null) {
            latestBookReview.delete();
        }

        Member member = memberRepository.findMemberBySeq(memberSeq);
        BookReview savedBookReview = bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo)
                .member(member)
                .starRating(bookReviewDto.getStarRating())
                .summary(bookReviewDto.getSummary())
                .isDeleted(false)
                .build());

        ResBookReviewByMemberDto resBookReviewByMemberDto = ResBookReviewByMemberDto.builder()
                .bookReviewSeq(savedBookReview.getSeq())
                .bookInfoSeq(savedBookReview.getBookInfo().getSeq())
                .memberSeq(savedBookReview.getMember().getSeq())
                .bookTitle(savedBookReview.getBookInfo().getTitle())
                .bookAuthor(savedBookReview.getBookInfo().getAuthor())
                .memberNickname(savedBookReview.getMember().getNickname())
                .starRating(savedBookReview.getStarRating())
                .summary(savedBookReview.getSummary())
                .createdDate(savedBookReview.getCreatedDate())
                .build();

        activityService.createNewActivity(savedBookReview.getSeq(), member, BOOK_REVIEW_CREATE, savedBookReview.getCreatedDate());

        return resBookReviewByMemberDto;
    }

    @Transactional
    @Override
    public ResModifiedBookReviewDto updateBookReview(ReqBookReviewDto reqBookReviewDto, Long memberSeq, Long bookReviewSeq) {
        BookReview bookReview = bookReviewRepository.findBySeq(bookReviewSeq);
        if (bookReview == null) {
            throw new NotFoundBookReviewException("올바르지 않은 요청입니다.");
        } else if (!bookReview.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        }

        bookReview.changeBookReview(reqBookReviewDto.getStarRating(), reqBookReviewDto.getSummary());

        ResModifiedBookReviewDto resModifiedBookReviewDto = ResModifiedBookReviewDto.builder()
                .bookReviewSeq(bookReview.getSeq())
                .bookInfoSeq(bookReview.getBookInfo().getSeq())
                .memberSeq(bookReview.getMember().getSeq())
                .memberNickname(bookReview.getMember().getNickname())
                .starRating(bookReview.getStarRating())
                .summary(bookReview.getSummary())
                .createdDate(bookReview.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        activityService.createNewActivity(bookReview.getSeq(), bookReview.getMember(), BOOK_REVIEW_UPDATE, bookReview.getLastModifiedDate());

        return resModifiedBookReviewDto;
    }

    @Override
    public Integer countBookReviewsByMemberSeq(Long memberSeq) {
        return bookReviewRepository.countBookReviewsByMemberSeq(memberSeq);
    }

    @Override
    public Integer countBookReviewsByBookInfoSeq(Long bookInfoSeq) {
        return bookReviewRepository.countBookReviewsByBookInfoSeq(bookInfoSeq);
    }
}

