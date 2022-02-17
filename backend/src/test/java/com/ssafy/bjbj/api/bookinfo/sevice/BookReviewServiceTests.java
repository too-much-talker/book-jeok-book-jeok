package com.ssafy.bjbj.api.bookinfo.sevice;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.MemberService;
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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BookReviewServiceTests {

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private EntityManager em;

    private ReqMemberDto reqMemberDto1;

    private ReqBookReviewDto reqBookReviewDto1;

    private BookInfo bookInfo1;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();
        em.flush();
        em.clear();

        reqMemberDto1 = ReqMemberDto.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
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
    }

    @DisplayName("북리뷰 수정 테스트")
    @Test
    public void updateBookReviewTests() {
        bookInfoRepository.save(bookInfo1);

        reqBookReviewDto1 = ReqBookReviewDto.builder()
                .bookInfoSeq(bookInfo1.getSeq())
                .starRating(3)
                .summary("test summary")
                .build();

        Member member1 = memberService.register(reqMemberDto1);

        ResBookReviewByMemberDto createBookReviewDto = bookReviewService.registerBookReview(reqBookReviewDto1, member1.getSeq());

        // 수정 전 체크
        BookReview bookReview1 = bookReviewRepository.findBySeq(createBookReviewDto.getBookReviewSeq());

        assertThat(bookReview1.getStarRating()).isEqualTo(createBookReviewDto.getStarRating());
        assertThat(bookReview1.getSummary()).isEqualTo(createBookReviewDto.getSummary());

        //수정
        ReqBookReviewDto modifiedBookReview = ReqBookReviewDto.builder()
                .bookInfoSeq(bookInfo1.getSeq())
                .starRating(1)
                .summary("modified summary")
                .build();

        ResModifiedBookReviewDto modifiedBookReviewDto = bookReviewService.updateBookReview(modifiedBookReview, member1.getSeq(), bookReview1.getSeq());

        //수정 후 체크
        BookReview bookReview2 = bookReviewRepository.findBySeq(modifiedBookReviewDto.getBookReviewSeq());

        assertThat(bookReview2.getStarRating()).isEqualTo(modifiedBookReview.getStarRating());
        assertThat(bookReview2.getSummary()).isEqualTo(modifiedBookReview.getSummary());
    }

    // 다대다 테이블 테스트 관련 논의 필요
//    @Test
//    public void findAllBookReviewsByMemberSeqTests() {
//        //등록전
//        List<ResponseBookReviewDto> bookReviews1 = bookReviewService.findAllBookReviewsByMemberSeq(1L);
//        assertThat(bookReviews1).isEmpty();
//        //등록
//        bookReviewService.registerBookReview(RequestBookReviewDto.builder()
//                .bookInfoSeq(1L)
//                .memberSeq(1L)
//                .starRating(4)
//                .summary("summary")
//                .build());
//        // 등록후
//        List<ResponseBookReviewDto> bookReviews2 = bookReviewService.findAllBookReviewsByMemberSeq(1L);
//        assertThat(bookReviews2).isNotEmpty();
//    }
}
