package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Subscribe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

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
                        .phoneNumber("010-1234-1234")
                        .build());

        // 회원 가입 후
        RequestMemberDto result2 = memberService.findMemberDtoByEmail(email1);
        assertThat(result2).isNotNull();
    }
//
//    @Test
//    public void subscribeMemberTests() {
//        //다른 회원 가입
//        boolean saveMember1 = memberService.saveMember(RequestMemberDto.builder()
//                .email("test@test.com")
//                .password("test1234")
//                .name("홍길동")
//                .nickname("hong")
//                .phoneNumber("010-1234-1234")
//                .build());
//        assertThat(saveMember1).isTrue();
//
//        Long fromMemberId = memberService.findMemberByEmail("test@test.com").getId();
//
//        boolean saveMember2 = memberService.saveMember(RequestMemberDto.builder()
//                .email("test2@test.com")
//                .password("test1234")
//                .name("홍길동")
//                .nickname("hongk")
//                .phoneNumber("010-1234-1234")
//                .build());
//        assertThat(saveMember2).isTrue();
//
//        Long toMemberId = memberService.findMemberByEmail("test2@test.com").getId();
//
//        System.out.println("1");
//        //구독 전
//        boolean canSubscribe = memberService.subscribeMember(fromMemberId, toMemberId);
//
//        System.out.println("2");
//        assertThat(canSubscribe).isTrue();
//        //구독 후
//        System.out.println("3");
//
//        boolean canSubscribe2 = memberService.subscribeMember(fromMemberId, toMemberId);
//        assertThat(canSubscribe2).isFalse();
//
//
//
//    }
}
