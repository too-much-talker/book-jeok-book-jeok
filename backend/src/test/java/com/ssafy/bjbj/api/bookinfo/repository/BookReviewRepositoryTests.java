package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

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


    @Test
    public void findAllReviewBookDtoByMemberSeqTests() {

        Member member = Member.builder()
                .email("bjbj@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();
        em.persist(member);
        //북리뷰 작성 전
        assertThat(bookReviewRepository.findAllBookReviewDtoByMemberSeq(member.getSeq())).isEmpty();

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(1L))
                .member(member)
                .starRating(3)
                .summary("테스트")
                .isDeleted(false)
                .build());
        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(2L))
                .member(member)
                .starRating(4)
                .summary("테스트2")
                .isDeleted(false)
                .build());

        em.flush();
        em.clear();
        //북리뷰 작성 후
        assertThat(bookReviewRepository.findAllBookReviewDtoByMemberSeq(member.getSeq()).stream().count()).isEqualTo(2);

    }

    @Test
    public void findAllReviewBookDtoByBookInfoSeqTests() {

        String date = "2021-12-20T12:30:00";

        String isbn = "isbn";
        String title = "title";
        String author = "author";
        String description = "description";
        Integer price = 100;
        String smallImgUrl = "smallImgUrl";
        String largeImgUrl = "largeImgUrl";
        Integer categoryId = 101;
        String categoryName = "categoryName";
        String publisher = "publisher";
        LocalDateTime publicationDate = LocalDateTime.parse(date);

        BookInfo bookInfo = BookInfo.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .description(description)
                .price(price)
                .smallImgUrl(smallImgUrl)
                .largeImgUrl(largeImgUrl)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .publisher(publisher)
                .publicationDate(publicationDate)
                .build();

        bookInfoRepository.save(bookInfo);

        Member member1 = Member.builder()
                .email("bjbj12@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hongkar")
                .phoneNumber("010-1234-3214")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();
        Member member2 = Member.builder()
                .email("bjbj13@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hongkur")
                .phoneNumber("010-1234-5152")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        //북리뷰 작성 전
        Long bookInfoSeq = bookInfo.getSeq();
        assertThat(bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq)).isEmpty();

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(bookInfoSeq))
                .member(member1)
                .starRating(3)
                .summary("테스트")
                .isDeleted(false)
                .build());

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(bookInfoSeq))
                .member(member2)
                .starRating(4)
                .summary("테스트2")
                .isDeleted(false)
                .build());

        em.flush();
        em.clear();
        //북리뷰 작성 후
        assertThat(bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq).stream().count()).isEqualTo(2);

    }

}
