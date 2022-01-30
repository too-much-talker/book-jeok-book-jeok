package com.ssafy.bjbj.api.bookinfo.sevice;

import com.ssafy.bjbj.api.bookinfo.dto.RequestBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.dto.ResponseBookReviewDto;
import com.ssafy.bjbj.api.bookinfo.service.BookReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class BookReviewServiceTests {

    @Autowired
    private BookReviewService bookReviewService;


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
