package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.service.MemberService;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupType;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@SpringBootTest
public class ReadingGroupBoardServiceTests {

    @Autowired
    private MemberService memberService;

    private Member member1;

    @BeforeEach
    public void setUp() throws InterruptedException {
        String email1 = "member1@bjbj.com";
        memberService.saveMember(RequestMemberDto.builder()
                .email(email1)
                .password("password")
                .name("name")
                .nickname("member1")
                .phoneNumber("010-9999-1111")
                .build());
        member1 = memberService.findMemberByEmail(email1);
    }

    //ReadingGroupService 제작 후 수정
    @DisplayName("독서모임 게시판 작성 테스트")
    @Test
    public void readingGroupBoardRegisterTest() {

    }

    @DisplayName("독서모임 게시판 상세 조회 테스트")
    @Test
    public void getDetailReadingGroupBoardTest() {

    }
}
