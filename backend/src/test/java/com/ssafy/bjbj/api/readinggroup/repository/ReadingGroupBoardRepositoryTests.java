package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.common.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class ReadingGroupBoardRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReadingGroupRepository readingGroupRepository;

    @Autowired
    private ReadingGroupBoardRepository readingGroupBoardRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private EntityManager em;

    private Member member1;
    private ReadingGroup readingGroup1;

    @BeforeEach
    public void setUp() throws InterruptedException {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();

        member1 = Member.builder()
                .email("member1@bjbj.com")
                .password("password")
                .name("name")
                .nickname("member1")
                .phoneNumber("010-9999-1111")
                .role(Role.MEMBER)
                .build();

        readingGroup1 = ReadingGroup.builder()
                .title("title")
                .content("test")
                .limitLevel(1)
                .maxMember(10)
                .deadline(LocalDateTime.parse("2023-12-20T12:30:00"))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.parse("2023-12-20T12:30:00"))
                .readingGroupType(ReadingGroupType.FREE)
                .member(member1)
                .build();
    }

    @DisplayName("독서모임 게시판 조회 테스트")
    @Test
    public void getReadingGroupBoardListTest() {
        memberRepository.save(member1);
        readingGroupRepository.save(readingGroup1);

        Pageable pageable = PageRequest.of(1, 10);
        List<ResReadingGroupArticleDto> readingGroupBoardDtos1 = readingGroupBoardRepository.findReadingGroupDtos(readingGroup1.getSeq(), pageable);
        assertThat(readingGroupBoardDtos1).isEmpty();

        ReadingGroupBoard readingGroupBoard1 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title1")
                .content("content1")
                .member(member1)
                .readingGroup(readingGroup1)
                .build());

        ReadingGroupBoard readingGroupBoard2 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title2")
                .content("content2")
                .member(member1)
                .readingGroup(readingGroup1)
                .build());

        ReadingGroupBoard readingGroupBoard3 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title3")
                .content("content3")
                .member(member1)
                .readingGroup(readingGroup1)
                .build());

        em.flush();
        em.clear();

        List<ResReadingGroupArticleDto> readingGroupBoardDtos = readingGroupBoardRepository.findReadingGroupDtos(readingGroup1.getSeq(), pageable);
        assertThat(readingGroupBoardDtos.size()).isEqualTo(3);
        assertThat(readingGroupBoardDtos.get(0).getReadingGroupBoardSeq()).isEqualTo(readingGroupBoard3.getSeq());
        assertThat(readingGroupBoardDtos.get(1).getReadingGroupBoardSeq()).isEqualTo(readingGroupBoard2.getSeq());
        assertThat(readingGroupBoardDtos.get(2).getReadingGroupBoardSeq()).isEqualTo(readingGroupBoard1.getSeq());
    }

}
