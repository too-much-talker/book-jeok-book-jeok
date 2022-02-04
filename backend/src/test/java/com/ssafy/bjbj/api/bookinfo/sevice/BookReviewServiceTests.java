package com.ssafy.bjbj.api.bookinfo.sevice;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResModifiedBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookReviewByMemberDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    private Member member1;


    @BeforeEach
    public void setUp() {
        String email = "setupEmail@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("setupNickanme")
                .phoneNumber("010-1111-2222")
                .build());
        member1 = memberService.findMemberByEmail(email);
    }

    @Test
    public void updateBookReviewTests() {
        ResponseBookReviewByMemberDto createBookReviewDto = bookReviewService.registerBookReview(RequestBookReviewDto.builder()
                .bookInfoSeq(1L)
                .memberSeq(member1.getSeq())
                .starRating(3)
                .summary("test summary")
                .build());
        // 수정 전 체크
        BookReview bookReview1 = bookReviewRepository.findBySeq(createBookReviewDto.getBookReviewSeq());

        assertThat(bookReview1.getStarRating()).isEqualTo(createBookReviewDto.getStarRating());
        assertThat(bookReview1.getSummary()).isEqualTo(createBookReviewDto.getSummary());

        //수정
        RequestBookReviewDto modifiedBookReview = RequestBookReviewDto.builder()
                .bookInfoSeq(1L)
                .memberSeq(member1.getSeq())
                .starRating(1)
                .summary("modified summary")
                .build();

        ResModifiedBookReviewDto modifiedBookReviewDto = bookReviewService.updateBookReview(modifiedBookReview);

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
