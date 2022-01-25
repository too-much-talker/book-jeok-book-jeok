package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("이메일 중복 확인 테스트")
    @Test
    public void existsByEmail() {
        // 1. 회원가입 전 -> 이메일 존재하지 않음
        String email = "bjbj_test@test.com";
        boolean result1 = memberRepository.existsByEmail(email);
        assertThat(result1).isFalse();

        // 2. 회원가입
        memberRepository.save(Member.builder()
                .email(email)
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5678")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        // 3. 회원가입 후 -> 이메일 존재
        em.flush();
        em.clear();
        boolean result2 = memberRepository.existsByEmail(email);
        assertThat(result2).isTrue();
    }

    @DisplayName("닉네임 중복 확인 테스트")
    @Test
    public void existsByNickname() {
        // 1. 회원가입 전 -> 닉네임 존재하지 않음
        String nickname = "bjbj";
        boolean result1 = memberRepository.existsByNickname(nickname);
        assertThat(result1).isFalse();

        // 2. 회원가입
        memberRepository.save(Member.builder()
                .email("bjbj_test@test.com")
                .password("test1234")
                .name("홍길동")
                .nickname(nickname)
                .phoneNumber("010-1234-5678")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        // 3. 회원가입 후 -> 닉네임 존재
        em.flush();
        em.clear();
        boolean result2 = memberRepository.existsByNickname(nickname);
        assertThat(result2).isTrue();
    }
    
    @DisplayName("회원가입 테스트")
    @Test
    public void save() {
        // 회원 가입 전
        Member member = Member.builder()
                .email("bjbj_test@test.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5678")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build();

        // 회원가입
        memberRepository.save(member);

        em.flush();
        em.clear();
        
        // db에서 가져온 회원
        Member savedMember = memberRepository.findById(member.getId()).get();

        assertThat(member.getId()).isEqualTo(savedMember.getId());
        assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(member.getName()).isEqualTo(savedMember.getName());
        assertThat(member.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(member.getPhoneNumber()).isEqualTo(savedMember.getPhoneNumber());
        assertThat(member.getExp()).isEqualTo(savedMember.getExp());
        assertThat(member.getPoint()).isEqualTo(savedMember.getPoint());
        assertThat(member.getRole()).isEqualTo(savedMember.getRole());
    }

    @DisplayName("응답용 회원 Dto 조회 레파지토리 테스트")
    @Test
    public void findResponseMemberDtoByEmail() {
        // 회원 가입 전
        String email = "test@test.com";
        ResponseMemberDto responseMemberDto1 = memberRepository.findResponseMemberDtoByEmail(email);
        assertThat(responseMemberDto1).isNull();

        // 회원 가입
        memberRepository.save(Member.builder()
                .email(email)
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .exp(0)
                .point(100)
                .build());

        // 회원 가입 후
        ResponseMemberDto responseMemberDto2 = memberRepository.findResponseMemberDtoByEmail(email);
        assertThat(responseMemberDto2).isNotNull();
    }

}