package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BooklogServiceImpl implements BooklogService {

    private final BooklogRepository booklogRepository;

    private final BookInfoRepository bookInfoRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long register(RequestBooklogDto reqBooklogDto) {
        BookInfo bookInfo = bookInfoRepository.findBySeq(reqBooklogDto.getBookInfoSeq());
        if (bookInfo == null) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        Member member = memberRepository.findBySeq(reqBooklogDto.getMemberSeq());

        LocalDateTime readDate = reqBooklogDto.getReadDate() == null ?
                null : LocalDateTime.parse(reqBooklogDto.getReadDate() + "T00:00:00");

        Booklog booklog = Booklog.builder()
                .title(reqBooklogDto.getTitle())
                .content(reqBooklogDto.getContent())
                .summary(reqBooklogDto.getSummary())
                .starRating(reqBooklogDto.getStarRating())
                .readDate(readDate)
                .isOpen(reqBooklogDto.getIsOpen())
                .views(0)
                .member(member)
                .bookInfo(bookInfo)
                .build();

        Booklog savedBooklog = booklogRepository.save(booklog);
        
        return savedBooklog.getSeq();
    }

    @Transactional
    @Override
    public Long update(Long booklogSeq, RequestBooklogDto reqBooklogDto) {
        Booklog savedBooklog = booklogRepository.findBySeq(booklogSeq);
        if (!savedBooklog.getBookInfo().getSeq().equals(reqBooklogDto.getBookInfoSeq())) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        savedBooklog.changeBooklog(reqBooklogDto);
        return savedBooklog.getSeq();
    }
}
