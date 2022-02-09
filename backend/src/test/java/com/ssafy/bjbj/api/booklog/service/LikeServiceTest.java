package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.booklog.exception.DuplicateLikeException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundLikeException;
import com.ssafy.bjbj.api.booklog.repository.LikeRepository;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LikeServiceTest {

    @Autowired
    private BooklogService booklogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    private BookInfo bookInfo1;

    private ReqMemberDto reqMemberDto1;

    @BeforeEach
    public void setUp() {
        reqMemberDto1 = ReqMemberDto.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .build();

        bookInfo1 = BookInfo.builder()
                .isbn("isbn")
                .title("제목")
                .author("저자")
                .description("설명")
                .price(10000)
                .smallImgUrl("smallImgUrl")
                .largeImgUrl("largeImgUrl")
                .categoryId(100)
                .categoryName("카테고리 이름")
                .publisher("출판사")
                .publicationDate(LocalDateTime.now())
                .build();
    }

    @DisplayName("북로그 좋아요 테스트")
    @Test
    public void likeTest() {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Booklog booklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("제목1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());

        Like findLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(findLike).isNull();

        likeService.like(booklog1.getSeq(), member1.getSeq());
        Like savedLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(savedLike).isNotNull();
        assertThat(savedLike.getBooklog().getSeq()).isEqualTo(booklog1.getSeq());
        assertThat(savedLike.getMember().getSeq()).isEqualTo(member1.getSeq());

        // 좋아요 중복 예외 처리 테스트
        assertThatThrownBy(() -> likeService.like(booklog1.getSeq(), member1.getSeq()))
                .isInstanceOf(DuplicateLikeException.class);
    }

    @DisplayName("북로그 좋아요 취소 테스트")
    @Test
    public void unLikeTest() {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Booklog booklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("제목1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());

        likeService.like(booklog1.getSeq(), member1.getSeq());
        Like savedLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(savedLike).isNotNull();

        // 좋아요 취소 테스트
        likeService.unLike(booklog1.getSeq(), member1.getSeq());
        Like deletedLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(deletedLike).isNull();

        // 좋아요 취소 후 좋아요 또 취소시 예외가 잘 터지는지 테스트
        assertThatThrownBy(() -> likeService.unLike(booklog1.getSeq(), member1.getSeq()))
                .isInstanceOf(NotFoundLikeException.class);
    }

    @DisplayName("북로그 좋아요 조회 테스트")
    @Test
    public void isLikeTest() {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Booklog booklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("제목1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());

        boolean isLike1 = likeService.isLike(booklog1.getSeq(), member1.getSeq());
        assertThat(isLike1).isFalse();

        likeService.like(booklog1.getSeq(), member1.getSeq());

        boolean isLike2 = likeService.isLike(booklog1.getSeq(), member1.getSeq());
        assertThat(isLike2).isTrue();
    }

}