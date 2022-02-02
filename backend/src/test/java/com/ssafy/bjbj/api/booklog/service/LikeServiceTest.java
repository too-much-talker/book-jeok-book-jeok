package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.booklog.exception.DuplicateLikeException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundLikeException;
import com.ssafy.bjbj.api.booklog.repository.LikeRepository;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    private Member member1;

    private Booklog booklog1;

    @BeforeEach
    public void setUp() {
        String email = "setupEmail@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("setupNickanme")
                .phoneNumber("010-0000-0000")
                .build());
        member1 = memberService.findMemberByEmail(email);

        Long booklogId = booklogService.register(RequestBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(1L)
                .title("title")
                .content("content")
                .summary("summary")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build());
        booklog1 = booklogService.findBySeq(booklogId);
    }

    @DisplayName("북로그 좋아요 테스트")
    @Test
    public void likeTest() {
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

}