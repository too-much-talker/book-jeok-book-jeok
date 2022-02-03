package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.ResBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BooklogServiceTest {

    @Autowired
    private BooklogService booklogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BooklogRepository booklogRepository;

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
                .phoneNumber("010-1111-2222")
                .build());
        member1 = memberService.findMemberByEmail(email);

        Long booklogSeq = booklogService.register(RequestBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(2L)
                .title("북로그 제목")
                .content("북로그 내용")
                .summary("북로그 한줄평")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build());
        booklog1 = booklogService.findBySeq(booklogSeq);
    }

    @DisplayName("북로그 작성 테스트")
    @Test
    public void booklog_register_test() {
        // 회원가입
        String email = "email@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .build());

        Member savedMember = memberService.findMemberByEmail(email);

        // 책 정보 저장
        /*
        책 정보의 경우 현재 등록 메서드가 없으므로 이미 db에 저장된 775번 책 정보를 사용
         */

        // 북로그 작성
        RequestBooklogDto reqBooklogDto = RequestBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(1L)
                .title("북로그 제목")
                .content("북로그 내용")
                .summary("북로그 한줄평")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();

        Long savedBooklogSeq = booklogService.register(reqBooklogDto);

        // 작성된 북로그 검증
        Booklog savedBooklog = booklogRepository.findBySeq(savedBooklogSeq);
        assertThat(reqBooklogDto.getMemberSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(reqBooklogDto.getBookInfoSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(reqBooklogDto.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(reqBooklogDto.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(reqBooklogDto.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(reqBooklogDto.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(reqBooklogDto.getReadDate()).isEqualTo(savedBooklog.getReadDate().toLocalDate().toString());
        assertThat(reqBooklogDto.getIsOpen()).isEqualTo(savedBooklog.isOpen());
    }

    @DisplayName("북로그 수정 테스트")
    @Test
    public void booklog_update_test() {
        // 회원가입
        String email = "email@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .build());
        Member savedMember = memberService.findMemberByEmail(email);

        // 책 정보 저장
        /*
        책 정보의 경우 현재 등록 메서드가 없으므로 이미 db에 저장된 775번 책 정보를 사용
         */

        // 북로그 작성
        RequestBooklogDto reqBooklogDto1 = RequestBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(1L)
                .title("북로그 제목")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .build();
        Long savedBooklogSeq1 = booklogService.register(reqBooklogDto1);

        // 북로그 수정
        RequestBooklogDto reqBooklogDto2 = RequestBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(1L)
                .title("북로그 제목2")
                .content("북로그 내용2")
                .summary("북로그 한줄평2")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        Long savedBooklogSeq2 = booklogService.update(savedBooklogSeq1, reqBooklogDto2);

        // 수정된 북로그 검증
        Booklog savedBooklog = booklogRepository.findBySeq(savedBooklogSeq2);
        assertThat(reqBooklogDto2.getMemberSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(reqBooklogDto2.getBookInfoSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(reqBooklogDto2.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(reqBooklogDto2.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(reqBooklogDto2.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(reqBooklogDto2.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(reqBooklogDto2.getReadDate()).isEqualTo(savedBooklog.getReadDate().toLocalDate().toString());
        assertThat(reqBooklogDto2.getIsOpen()).isEqualTo(savedBooklog.isOpen());
    }

    @DisplayName("북로그 삭제 테스트")
    @Test
    public void booklog_remove_test() {
        // 회원가입
        String email = "email@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .build());
        Member savedMember = memberService.findMemberByEmail(email);

        // 책 정보 저장
        /*
        책 정보의 경우 현재 등록 메서드가 없으므로 이미 db에 저장된 775번 책 정보를 사용
         */

        // 북로그 작성
        RequestBooklogDto reqBooklogDto = RequestBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(1L)
                .title("북로그 제목")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .build();
        Long savedBooklogSeq = booklogService.register(reqBooklogDto);

        booklogService.remove(savedBooklogSeq, savedMember.getSeq());

        // 삭제 검증
        Booklog deletedBooklog = booklogRepository.findBySeq(savedBooklogSeq);
        assertThat(deletedBooklog.isDeleted()).isTrue();
    }

    @DisplayName("북로그 공개여부 수정 테스트")
    @Test
    public void booklogIsOpenChangeTest() {
        // 북로그 작성
        RequestBooklogDto reqBooklogDto = RequestBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(1L)
                .title("북로그 제목")
                .content("북로그 내용")
                .summary("북로그 한줄평")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        Long savedBooklogSeq = booklogService.register(reqBooklogDto);

        // 저장된 북로그 공개여부 확인
        Booklog savedBooklog = booklogService.findBySeq(savedBooklogSeq);
        assertThat(savedBooklog.isOpen()).isTrue();

        // 공개여부 수정
        booklogService.changeIsOpen(savedBooklogSeq, member1.getSeq(), false);

        // 수정된 공개여부 검증
        Booklog changedBooklog = booklogService.findBySeq(savedBooklogSeq);
        assertThat(changedBooklog.isOpen()).isFalse();
    }

    @DisplayName("북로그 조회 테스트")
    @Test
    public void booklogGetTest() {
        ResBooklogDto resBooklogDto1 = booklogService.getResBooklogDtoBooklog(booklog1.getSeq(), member1.getSeq());
        assertThat(resBooklogDto1).isNotNull();
        assertThat(resBooklogDto1.getBooklogSeq()).isEqualTo(booklog1.getSeq());
        assertThat(resBooklogDto1.getMemberSeq()).isEqualTo(member1.getSeq());
        assertThat(resBooklogDto1.getTitle()).isEqualTo(booklog1.getTitle());
        assertThat(resBooklogDto1.getContent()).isEqualTo(booklog1.getContent());
        assertThat(resBooklogDto1.getSummary()).isEqualTo(booklog1.getSummary());
        assertThat(resBooklogDto1.getStarRating()).isEqualTo(booklog1.getStarRating());
        assertThat(resBooklogDto1.getReadDate()).isEqualTo(booklog1.getReadDate() == null ? null : booklog1.getReadDate().toLocalDate());
        assertThat(resBooklogDto1.getIsOpen()).isEqualTo(booklog1.isOpen());
        assertThat(resBooklogDto1.getViews()).isEqualTo(booklog1.getViews());
        assertThat(resBooklogDto1.getCreatedDate()).isEqualTo(booklog1.getCreatedDate().toLocalDate());

        // 조회수 증가 테스트
        for (int i = 1; i <= 5; i++) {
            ResBooklogDto resBooklogDto2 = booklogService.getResBooklogDtoBooklog(booklog1.getSeq(), member1.getSeq() + 1);
            assertThat(resBooklogDto2.getViews()).isEqualTo(i);
        }
    }

}