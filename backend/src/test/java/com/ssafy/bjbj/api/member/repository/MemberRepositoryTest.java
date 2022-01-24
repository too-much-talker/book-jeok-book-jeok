package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

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

}