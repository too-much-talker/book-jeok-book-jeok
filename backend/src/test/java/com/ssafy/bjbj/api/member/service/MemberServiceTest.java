package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("응답용 회원 Dto 조회 서비스 메서드 테스트")
    @Test
    public void findResponseMemberDtoByEmail() {
        // 회원 가입 전
        String email = "test@test.com";
        ResponseMemberDto responseMemberDto1 = memberService.findResponseMemberDtoByEmail(email);
        assertThat(responseMemberDto1).isNull();

        // 회원 가입
        memberService.saveMember(RequestMemberDto.builder()
                        .email(email)
                        .password("test1234")
                        .name("홍길동")
                        .nickname("hong")
                        .phoneNumber("010-1234-5678")
                        .build());

        // 회원 가입 후
        ResponseMemberDto responseMemberDto2 = memberService.findResponseMemberDtoByEmail(email);
        assertThat(responseMemberDto2).isNotNull();
    }

    @DisplayName("응답용 회원 Dto 조회 서비스 메서드 테스트")
    @Test
    public void updateMember() {

        String email = "qwerty@test.com";
        String password = "test1234";
        String name = "홍길동";
        String nickname = "hong";
        String phoneNumber = "010-1234-5678";

        // 회원 등록
        RequestMemberDto memberDto = RequestMemberDto.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1111-1111")
                .build();
        memberService.saveMember(memberDto);

        // 정보를 수정할 회원 id와 수정된 회원 정보
        RequestMemberDto newMemberDto = RequestMemberDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .build();
        Long id = memberService.findMemberByEmail(email).getId();

        // 회원 정보 변경
        memberService.updateMember(newMemberDto, id);
        Member updatedMember = memberService.findMemberByEmail(email);

        // 변경 후
        assertThat(passwordEncoder.matches(password, updatedMember.getPassword())).isTrue();
        assertThat(updatedMember.getName()).isEqualTo(name);
        assertThat(updatedMember.getNickname()).isEqualTo(nickname);
        assertThat(updatedMember.getPhoneNumber()).isEqualTo(phoneNumber);


    }

}
