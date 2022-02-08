package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ReadingGroupBoardServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private ReqMemberDto reqMemberDto1;

    @BeforeEach
    public void setUp() throws InterruptedException {
        memberRepository.deleteAll();

        reqMemberDto1 = ReqMemberDto.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .build();
    }

//    //ReadingGroupService 제작 후 수정
//    @DisplayName("독서모임 게시판 작성 테스트")
//    @Test
//    public void readingGroupBoardRegisterTest() {
//
//    }
//
//    @DisplayName("독서모임 게시판 상세 조회 테스트")
//    @Test
//    public void getDetailReadingGroupBoardTest() {
//
//    }

}
