package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
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

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    private final BookInfoRepository bookInfoRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public ResponseBookReviewDto registerBookReview(RequestBookReviewDto bookReviewDto) {


        BookInfo bookInfo = bookInfoRepository.findBySeq(bookReviewDto.getBookInfoSeq());

        Member member = memberRepository.findMemberBySeq(bookReviewDto.getMemberSeq());

        ResponseBookReviewDto latestBookReviewDto = bookReviewRepository.findLatestBookReviewDtoByBookInfoAndMember(bookInfo.getSeq(), member.getSeq());

        if (latestBookReviewDto != null) {
            BookReview bookReview = bookReviewRepository.findBySeq(latestBookReviewDto.getSeq());
            bookReview.changeBookReviewDeleted(true);

        }

        BookReview savedBookReview = bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo)
                .member(member)
                .starRating(bookReviewDto.getStarRating())
                .summary(bookReviewDto.getSummary())
                .isDeleted(false)
                .build());

        return ResponseBookReviewDto.builder()
                .seq(savedBookReview.getSeq())
                .starRating(savedBookReview.getStarRating())
                .summary(savedBookReview.getSummary())
                .createdDate(savedBookReview.getCreatedDate())
                .build();
    }

}
