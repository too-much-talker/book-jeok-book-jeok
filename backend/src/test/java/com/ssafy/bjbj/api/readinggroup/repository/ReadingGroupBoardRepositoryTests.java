package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupType;
import com.ssafy.bjbj.common.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
public class ReadingGroupBoardRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReadingGroupRepository readingGroupRepository;

    @Autowired
    private ReadingGroupBoardRepository readingGroupBoardRepository;

    private Member member1;
    private ReadingGroup readingGroup1;

    @BeforeEach
    public void setUp() throws InterruptedException {
        memberRepository.deleteAll();
        readingGroupRepository.deleteAll();

        String email1 = "member1@bjbj.com";
        member1 = memberRepository.save(Member.builder()
                .email(email1)
                .password("password")
                .name("name")
                .nickname("member1")
                .phoneNumber("010-9999-1111")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        readingGroup1 = readingGroupRepository.save(ReadingGroup.builder()
                .title("title")
                .content("test")
                .views(0)
                .limitPoint(0)
                .maxMember(10)
                .deadline(LocalDateTime.parse("2023-12-20T12:30:00"))
                .status(Status.PRE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.parse("2023-12-20T12:30:00"))
                .readingGroupType(ReadingGroupType.CASUAL)
                .isDeleted(false)
                .member(memberRepository.findMemberBySeq(1L))
                .build());
    }

}
