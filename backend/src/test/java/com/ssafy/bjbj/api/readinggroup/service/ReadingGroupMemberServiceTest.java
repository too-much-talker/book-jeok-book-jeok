package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupMemberException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ReadingGroupMemberServiceTest {

    @Autowired
    private ReadingGroupMemberService readingGroupMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private ReadingGroupMemberRepository readingGroupMemberRepository;

    @Autowired
    private ReadingGroupService readingGroupService;

    @Autowired
    private EntityManager em;

    private Member member1, member2;

    private ReqReadingGroupDto reqReadingGroupDto1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .role(Role.MEMBER)
                .build();

        member2 = Member.builder()
                .email("test2@test.com")
                .password("password1")
                .name("name2")
                .nickname("nickname2")
                .phoneNumber("010-0000-0002")
                .role(Role.MEMBER)
                .build();

        reqReadingGroupDto1 = ReqReadingGroupDto.builder()
                .title("title1")
                .content("content1")
                .limitLevel(10)
                .maxMember(5)
                .deadline("2022-02-28")
                .startDate("2022-03-01")
                .endDate("2022-03-31")
                .readingGroupType("FREE")
                .days(Arrays.asList("MON", "TUE"))
                .build();
    }

    @DisplayName("독서 모임 참여신청 서비스 테스트")
    @Test
    public void readingGroupJoinTest() {
        // 독서 모임 등록
        memberRepository.save(member1);
        memberRepository.save(member2);
        ReadingGroup savedReadingGroup = readingGroupService.register(reqReadingGroupDto1, member1.getSeq());

        // 독서 모임 참여신청
        readingGroupMemberService.join(savedReadingGroup.getSeq(), member2.getSeq());
        em.flush();
        em.clear();

        /**
         * 검증
         */
        ReadingGroupMember findReadingGroupMember = readingGroupMemberRepository.findByReadingGroupSeqAndMemberSeq(savedReadingGroup.getSeq(), member2.getSeq())
                .orElseThrow(() -> new NotFoundReadingGroupMemberException("올바르지 않은 요청입니다."));
        assertThat(findReadingGroupMember.getReadingGroup().getSeq()).isEqualTo(savedReadingGroup.getSeq());
        assertThat(findReadingGroupMember.getMember().getSeq()).isEqualTo(member2.getSeq());
    }

    @DisplayName("독서 모임 참여신청 취소 서비스 테스트")
    @Test
    public void readingGroupUnJoinTest() {
        // 독서 모임 등록
        memberRepository.save(member1);
        memberRepository.save(member2);
        ReadingGroup savedReadingGroup = readingGroupService.register(reqReadingGroupDto1, member1.getSeq());

        /**
         * 독서 모임 참여신청
         */
        readingGroupMemberService.join(savedReadingGroup.getSeq(), member2.getSeq());
        em.flush();
        em.clear();

        /**
         * 독서 모임 참여신청 취소
         */
        readingGroupMemberService.unJoin(savedReadingGroup.getSeq(), member2.getSeq());

        /**
         * 검증
         */
        Optional<ReadingGroupMember> optFindReadingGroupMember = readingGroupMemberRepository.findByReadingGroupSeqAndMemberSeq(savedReadingGroup.getSeq(), member2.getSeq());
        assertThat(optFindReadingGroupMember.isPresent()).isFalse();
    }



}