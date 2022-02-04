package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
            bookReview.delete();
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
            latestBookReview.delete();
        }

            BookReview savedBookReview = bookReviewRepository.save(BookReview.builder()
                    .bookInfo(bookInfo)
                    .member(member)
                    .starRating(bookReviewDto.getStarRating())
                    .summary(bookReviewDto.getSummary())
                    .isDeleted(false)
                    .build());

            return ResponseBookReviewByMemberDto.builder()
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
    }

    @Transactional
    @Override
    public ResModifiedBookReviewDto updateBookReview(RequestBookReviewDto requestBookReviewDto) {

        Long bookInfoSeq = requestBookReviewDto.getBookInfoSeq();
        Long memberSeq = requestBookReviewDto.getMemberSeq();

        BookReview bookReview = bookReviewRepository.findLatestBookReviewByBookInfoAndMember(bookInfoSeq, memberSeq);

        if (bookReview == null) {
            return null;
        }

        bookReview.changeBookReview(requestBookReviewDto.getStarRating(), requestBookReviewDto.getSummary());

        return ResModifiedBookReviewDto.builder()
                .bookReviewSeq(bookReview.getSeq())
                .bookInfoSeq(bookReview.getBookInfo().getSeq())
                .memberSeq(bookReview.getMember().getSeq())
                .memberNickname(bookReview.getMember().getNickname())
                .starRating(bookReview.getStarRating())
                .summary(bookReview.getSummary())
                .createdDate(bookReview.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();
    }
}

