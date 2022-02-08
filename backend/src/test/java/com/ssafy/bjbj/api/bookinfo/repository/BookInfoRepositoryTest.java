package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookSearchDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoSmallDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoDto;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class BookInfoRepositoryTest {

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private EntityManager em;

    private BookInfo bookInfo1;

    private BookInfo bookInfo2;

    private Member member1;
    private Member member2;

    private BookReview bookReview1;
    private BookReview bookReview2;

    @BeforeEach
    public void setUp() {

        member1 = Member.builder()
                .email("bjbj@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("nickname")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();

        member2 = Member.builder()
                .email("bjbj123@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hhhhhh")
                .phoneNumber("010-9876-5789")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build();

        String date = "2022-12-20T12:30:00";

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
                .publicationDate(LocalDateTime.parse("2023-02-20T12:30:00"))
                .build();

    }

    @DisplayName("응답용 책 정보 Dto를 seq로 조회하는 repository 테스트")
    @Test
    public void findBookInfoBySeq() {
        bookReviewRepository.deleteAll();

        em.persist(bookInfo1);
        memberRepository.save(member1);
        bookReview1 = BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(bookInfo1.getSeq()))
                .member(memberRepository.findBySeq(member1.getSeq()))
                .starRating(4)
                .summary("test summary")
                .isDeleted(false)
                .build();
        bookReviewRepository.save(bookReview1);

        memberRepository.save(member2);
        bookReview2 = BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(bookInfo1.getSeq()))
                .member(memberRepository.findBySeq(member2.getSeq()))
                .starRating(5)
                .summary("test summary")
                .isDeleted(false)
                .build();
        bookReviewRepository.save(bookReview2);

        em.flush();
        em.clear();

        ResBookInfoDto savedBookInfo = bookInfoRepository.findResBookInfoDtoBySeq(bookInfo1.getSeq());

        assertThat(bookInfo1.getSeq()).isEqualTo(savedBookInfo.getSeq());
        assertThat(bookInfo1.getIsbn()).isEqualTo(savedBookInfo.getIsbn());
        assertThat(bookInfo1.getTitle()).isEqualTo(savedBookInfo.getTitle());
        assertThat(bookInfo1.getAuthor()).isEqualTo(savedBookInfo.getAuthor());
        assertThat(bookInfo1.getDescription()).isEqualTo(savedBookInfo.getDescription());
        assertThat(bookInfo1.getPrice()).isEqualTo(savedBookInfo.getPrice());
        assertThat(bookInfo1.getSmallImgUrl()).isEqualTo(savedBookInfo.getSmallImgUrl());
        assertThat(bookInfo1.getLargeImgUrl()).isEqualTo(savedBookInfo.getLargeImgUrl());
        assertThat(bookInfo1.getCategoryId()).isEqualTo(savedBookInfo.getCategoryId());
        assertThat(bookInfo1.getCategoryName()).isEqualTo(savedBookInfo.getCategoryName());
        assertThat(bookInfo1.getPublisher()).isEqualTo(savedBookInfo.getPublisher());
        assertThat(bookInfo1.getPublicationDate().toLocalDate()).isEqualTo(savedBookInfo.getPublicationDate());
        assertThat(savedBookInfo.getStarRating()).isEqualTo((bookReview1.getStarRating() + bookReview2.getStarRating()) / 2.0);
    }

    @DisplayName("책 정보 Dto List를 request에 맞게 조회하는 repository 테스트")
    @Test
    public void findListByRequest() throws InterruptedException {
        bookReviewRepository.deleteAll();

        bookInfoRepository.save(bookInfo1);
        bookInfoRepository.save(bookInfo2);
        memberRepository.save(member1);
        bookReview1 = BookReview.builder()
                .bookInfo(bookInfoRepository.findBySeq(bookInfo1.getSeq()))
                .member(memberRepository.findBySeq(member1.getSeq()))
                .starRating(4)
                .summary("test summary")
                .isDeleted(false)
                .build();
        bookReviewRepository.save(bookReview1);
        em.flush();
        em.clear();

        List<ResBookInfoSmallDto> resBookListDto1 = bookInfoRepository.findListByRequest(new ReqBookSearchDto(1, 10, "title", "2", "latest"));
        assertThat(resBookListDto1.get(0).getSeq()).isEqualTo(bookInfo2.getSeq());

        List<ResBookInfoSmallDto> resBookListDto2 = bookInfoRepository.findListByRequest(new ReqBookSearchDto(1, 10, "title", "", "star"));
        assertThat(resBookListDto2.get(0).getSeq()).isEqualTo(bookInfo1.getSeq());

    }
}
