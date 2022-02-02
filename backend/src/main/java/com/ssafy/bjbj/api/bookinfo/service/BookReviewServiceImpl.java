package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    private final BookInfoRepository bookInfoRepository;

    private final MemberRepository memberRepository;

    @Override
    public BookReview findBookReviewByBookSeq(Long bookReviewSeq) {
        return bookReviewRepository.findBySeq(bookReviewSeq);
    }

    @Override
    public List<ResponseBookReviewByMemberDto> findAllBookReviewsByMemberSeq(Long memberSeq) {
        return bookReviewRepository.findAllBookReviewDtoByMemberSeq(memberSeq);
    }

    @Override
    public List<ResponseBookReviewByBookInfoDto> findAllBookReviewsByBookInfoSeq(Long bookInfoSeq) {
        return bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq);
    }

    @Transactional
    @Override
    public boolean deleteBookReview(Long bookReviewSeq) {

        BookReview bookReview = bookReviewRepository.findBySeq(bookReviewSeq);

        if (!bookReview.isDeleted()) {
            bookReview.changeBookReviewDeleted(true);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public ResponseBookReviewByMemberDto registerBookReview(RequestBookReviewDto bookReviewDto) {

        BookInfo bookInfo = bookInfoRepository.findBySeq(bookReviewDto.getBookInfoSeq());
        Member member = memberRepository.findMemberBySeq(bookReviewDto.getMemberSeq());

        BookReview latestBookReview = bookReviewRepository.findLatestBookReviewByBookInfoAndMember(bookReviewDto.getBookInfoSeq(), bookReviewDto.getMemberSeq());

        if (latestBookReview != null) {
            latestBookReview.changeBookReviewDeleted(true);
        }

            BookReview savedBookReview = bookReviewRepository.save(BookReview.builder()
                    .bookInfo(bookInfo)
                    .member(member)
                    .starRating(bookReviewDto.getStarRating())
                    .summary(bookReviewDto.getSummary())
                    .isDeleted(false)
                    .build());

            return ResponseBookReviewByMemberDto.builder()
                    .seq(savedBookReview.getSeq())
                    .bookInfoSeq(savedBookReview.getBookInfo().getSeq())
                    .memberSeq(savedBookReview.getMember().getSeq())
                    .bookTitle(savedBookReview.getBookInfo().getTitle())
                    .bookAuthor(savedBookReview.getBookInfo().getAuthor())
                    .memberNickname(savedBookReview.getMember().getNickname())
                    .starRating(savedBookReview.getStarRating())
                    .summary(savedBookReview.getSummary())
                    .createdDate(savedBookReview.getCreatedDate())
                    .build();
    }
}

