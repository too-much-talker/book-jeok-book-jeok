package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class BookReviewRepositoryTests {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("북리뷰 추가 테스트")
    @Test
    public void registerReviewTest() {
        BookReview bookReview = BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(1L))
                .member(memberRepository.findBySeq(1L))
                .starRating(4)
                .summary("test summary")
                .isDeleted(false)
                .build();

        bookReviewRepository.save(bookReview);

        em.flush();
        em.clear();

        BookReview savedBookReview = bookReviewRepository.findBySeq(bookReview.getSeq());

        assertThat(bookReview.getSeq()).isEqualTo(savedBookReview.getSeq());
    }
}
