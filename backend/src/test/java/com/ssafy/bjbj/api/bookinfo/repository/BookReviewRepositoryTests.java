package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    private Member member1, member2;

    private BookInfo bookInfo1, bookInfo2;

    private BookReview bookReview1;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();

        member2 = Member.builder()
                .email("test2@test.com")
                .password("password2")
                .name("name2")
                .nickname("nickname2")
                .phoneNumber("010-0000-0002")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();

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
        LocalDate date = LocalDate.now();
        LocalDateTime publicationDate = LocalDateTime.of(date, LocalTime.now());

        bookInfo1 = BookInfo.builder()
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

        bookInfo2 = BookInfo.builder()
                .isbn(isbn + "2")
                .title(title + "2")
                .author(author + "2")
                .description(description + "2")
                .price(price)
                .smallImgUrl(smallImgUrl + "2")
                .largeImgUrl(largeImgUrl + "2")
                .categoryId(categoryId)
                .categoryName(categoryName + "2")
                .publisher(publisher + "2")
                .publicationDate(LocalDateTime.of(date.plusDays(1), LocalTime.now()))
                .build();
    }

    @DisplayName("북리뷰 추가 테스트")
    @Test
    public void registerReviewTest() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        bookReview1 = BookReview.builder()
                .bookInfo(bookInfo1)
                .member(member1)
                .starRating(4)
                .summary("test summary")
                .isDeleted(false)
                .build();

        bookReviewRepository.save(bookReview1);

        em.flush();
        em.clear();

        BookReview savedBookReview = bookReviewRepository.findBySeq(bookReview1.getSeq());

        assertThat(bookReview1.getSeq()).isEqualTo(savedBookReview.getSeq());
    }

    @DisplayName("내 북리뷰 목록 조회 테스트")
    @Test
    public void findAllReviewBookDtoByMemberSeqTests() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);
        bookInfoRepository.save(bookInfo2);

        //북리뷰 작성 전
        assertThat(bookReviewRepository.findAllBookReviewDtoByMemberSeq(member1.getSeq())).isEmpty();

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo1)
                .member(member1)
                .starRating(3)
                .summary("테스트")
                .isDeleted(false)
                .build());
        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo2)
                .member(member1)
                .starRating(4)
                .summary("테스트2")
                .isDeleted(false)
                .build());

        em.flush();
        em.clear();

        //북리뷰 작성 후
        List<ResBookReviewByMemberDto> resBookReviewByMemberDtos = bookReviewRepository.findAllBookReviewDtoByMemberSeq(member1.getSeq());
        assertThat(resBookReviewByMemberDtos.size()).isEqualTo(2);
    }

    @DisplayName("책정보별 북리뷰 목록 조회 테스트")
    @Test
    public void findAllReviewBookDtoByBookInfoSeqTests() {
        bookInfoRepository.save(bookInfo1);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //북리뷰 작성 전
        Long bookInfoSeq = bookInfo1.getSeq();
        assertThat(bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq)).isEmpty();

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo1)
                .member(member1)
                .starRating(3)
                .summary("테스트")
                .isDeleted(false)
                .build());

        bookReviewRepository.save(BookReview.builder()
                .bookInfo(bookInfo1)
                .member(member2)
                .starRating(4)
                .summary("테스트2")
                .isDeleted(false)
                .build());

        em.flush();
        em.clear();

        //북리뷰 작성 후
        List<ResBookReviewByBookInfoDto> resBookReviewByBookInfoDtos = bookReviewRepository.findAllBookReviewDtoByBookInfoSeq(bookInfoSeq);
        assertThat(resBookReviewByBookInfoDtos.size()).isEqualTo(2);
    }

}
