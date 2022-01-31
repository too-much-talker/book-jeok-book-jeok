package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
                .bookInfoSeq(775L)
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
                .bookInfoSeq(775L)
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
                .bookInfoSeq(775L)
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
                .bookInfoSeq(775L)
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

}