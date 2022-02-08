package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupType;
import com.ssafy.bjbj.common.entity.Status;
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
    private BookReviewRepository bookReviewRepository;

    @Autowired
    private EntityManager em;

    private Member member1;
    private ReadingGroup readingGroup1;

    @BeforeEach
    public void setUp() throws InterruptedException {
        readingGroupBoardRepository.deleteAll();
        em.flush();
        em.clear();
        readingGroupRepository.deleteAll();
        em.flush();
        em.clear();
        bookReviewRepository.deleteAll();
        em.flush();
        em.clear();
        memberRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = memberRepository.save(Member.builder()
                .email("member1@bjbj.com")
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
                .member(member1)
                .build());
    }

    @DisplayName("독서모임 게시판 조회 테스트")
    @Test
    public void getReadingGroupBoardListTest() {

        Pageable pageable = PageRequest.of(1, 10);
        List<ResReadingGroupArticleDto> readingGroupBoardDtos1 = readingGroupBoardRepository.findReadingGroupDtos(readingGroup1.getSeq(), pageable);
        assertThat(readingGroupBoardDtos1).isEmpty();

        ReadingGroupBoard readingGroupBoard1 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title1")
                .content("content1")
                .views(0)
                .member(member1)
                .readingGroup(readingGroup1)
                .build());

        ReadingGroupBoard readingGroupBoard2 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title2")
                .content("content2")
                .views(0)
                .member(member1)
                .readingGroup(readingGroup1)
                .build());

        ReadingGroupBoard readingGroupBoard3 = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title("title3")
                .content("content3")
                .views(0)
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
