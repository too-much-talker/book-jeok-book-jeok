package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    public void findMemberDtoByEmail() {
        // 회원 가입 전
        String email1 = "test@test.com";
        RequestMemberDto result1 = memberService.findMemberDtoByEmail(email1);
        assertThat(result1).isNull();

        // 회원 가입
        memberService.saveMember(RequestMemberDto.builder()
                        .email(email1)
                        .password("test1234")
                        .name("홍길동")
                        .nickname("hong")
                        .build());

        // 회원 가입 후
        RequestMemberDto result2 = memberService.findMemberDtoByEmail(email1);
        assertThat(result2).isNotNull();
    }

}
