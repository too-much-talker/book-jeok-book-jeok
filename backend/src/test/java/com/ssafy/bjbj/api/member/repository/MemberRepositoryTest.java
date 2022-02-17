package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.entity.Activity;
import com.ssafy.bjbj.api.member.enums.ActivityType;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
        em.flush();
        em.clear();
    }

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
                .role(Role.MEMBER)
                .build();

        // 회원가입
        memberRepository.save(member);

        em.flush();
        em.clear();

        // db에서 가져온 회원
        Member savedMember = memberRepository.findBySeq(member.getSeq());

        assertThat(member.getSeq()).isEqualTo(savedMember.getSeq());
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
        ResLoginMemberDto responseMemberDto1 = memberRepository.findResLoginMemberDtoByEmail(email);
        assertThat(responseMemberDto1).isNull();

        // 회원 가입
        memberRepository.save(Member.builder()
                .email(email)
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .build());

        // 회원 가입 후
        ResLoginMemberDto responseMemberDto2 = memberRepository.findResLoginMemberDtoByEmail(email);
        assertThat(responseMemberDto2).isNotNull();
    }

    @DisplayName("ID로 Point Select 테스트")
    @Test
    public void findPointBySeq() {
        // 회원가입
        Integer point = 100;
        Member member = Member.builder()
                .email("bjbj@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);
        em.flush();
        em.clear();

        // 경험치 select
        Integer savedPoint = memberRepository.findPointBySeq(member.getSeq());

        // 검증
        assertThat(point).isEqualTo(savedPoint);
    }

    @DisplayName("ID로 Exp Select 테스트")
    @Test
    public void findExpBySeq() {
        // 회원가입
        Integer exp = 0;
        Member member = Member.builder()
                .email("bjbj@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);
        em.flush();
        em.clear();

        // exp select
        Integer savedExp = memberRepository.findExpBySeq(member.getSeq());

        // 검증
        assertThat(exp).isEqualTo(savedExp);
    }

    @DisplayName("ID로 활동량 카운트 Select 테스트")
    @Test
    public void findAllActivityCountBySeq() {
        // 회원 가입
        Member member = Member.builder()
                .email("bjbj@bjbj.com")
                .password("test1234")
                .name("홍길동")
                .nickname("hong")
                .phoneNumber("010-1234-5789")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        // long -> Long 자동 형변환
        for (long seq = 105L; seq > 100L; seq--) {
            // "2022-01-01 ~ 2022-01-05 날짜별 활동 1개
            LocalDateTime parseDateTime = LocalDateTime.parse("2022-01-0" + String.valueOf(seq - 100L) + "T12:30:00");
            activityRepository.save(Activity.builder()
                    .referSeq(seq)
                    .activityType(ActivityType.BOOKLOG_CREATE)
                    .time(parseDateTime)
                    .member(member)
                    .build());
        }

        // 2021-12-20 3개 활동
        for (long seq = 100L; seq >= 98L; seq--) {
            activityRepository.save(Activity.builder()
                    .referSeq(seq)
                    .activityType(ActivityType.BOOKLOG_CREATE)
                    .time(LocalDateTime.parse("2021-12-20T09:00:00"))
                    .member(member)
                    .build());
        }

        /**
         * 2022-01-01 ~ 2022-01-05 1개씩 총 5개
         * 2021-12-20 3개 -> 1개
         * 총 6개
         */
        List<ActivityCountDto> activityCountDtos = memberRepository.findAllActivityCountDtoBySeq(member.getSeq());
        assertThat(activityCountDtos.size()).isEqualTo(6);

        int index = 0;
        for (long seq = 105L; seq > 100L; seq--) {
            ActivityCountDto activityCountDto = activityCountDtos.get(index);
            assertThat(activityCountDto.getDate()).isEqualTo("2022-01-0" + String.valueOf(seq - 100L));
            assertThat(activityCountDto.getCount()).isEqualTo(1);
            ++index;
        }

        ActivityCountDto activityCountDto = activityCountDtos.get(index);
        assertThat(activityCountDto.getDate()).isEqualTo("2021-12-20");
        assertThat(activityCountDto.getCount()).isEqualTo(3);
    }

}