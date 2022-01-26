package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import org.junit.jupiter.api.DisplayName;
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
    
    @DisplayName("ID로 활동량 카운트 get 테스트")
    @Test
    public void getAllActivityCountsTest() {
        // 추후에 북로그, 챌린지 관련 API 개발 후에 이 메서드 테스트 로직 추가 필요
    }

}
