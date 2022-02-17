package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupDate;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.enums.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ReadingGroupServiceTest {

    @Autowired
    private ReadingGroupService readingGroupService;

    @Autowired
    private ReadingGroupRepository readingGroupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    private ReqReadingGroupDto reqReadingGroupDto1;

    private Member member1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();
        em.clear();
        em.clear();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
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

    @DisplayName("독서 모임 모집 포스팅 등록 서비스 메서드 테스트")
    @Test
    public void readingGroupRegisterTest() {
        memberRepository.save(member1);

        // 독서 모임 모집 모스팅 등록
        ReadingGroup savedReadingGroup = readingGroupService.register(reqReadingGroupDto1, member1.getSeq());
        em.flush();
        em.clear();

        // 저장된 독서 모임 모집 포스팅 가져오기
        ReadingGroup findReadingGroup = readingGroupRepository.findBySeq(savedReadingGroup.getSeq())
                .orElseThrow(() -> new NotFoundReadingGroupException("올바르지 않은 요청입니다."));

        // 독서 모임 모집 포스팅 정보 검증
        assertThat(findReadingGroup.getTitle()).isEqualTo(reqReadingGroupDto1.getTitle());
        assertThat(findReadingGroup.getContent()).isEqualTo(reqReadingGroupDto1.getContent());
        assertThat(findReadingGroup.getLimitLevel()).isEqualTo(reqReadingGroupDto1.getLimitLevel());
        assertThat(findReadingGroup.getMaxMember()).isEqualTo(reqReadingGroupDto1.getMaxMember());
        assertThat(findReadingGroup.getDeadline().toLocalDate().toString()).isEqualTo(reqReadingGroupDto1.getDeadline());
        assertThat(findReadingGroup.getStartDate().toLocalDate().toString()).isEqualTo(reqReadingGroupDto1.getStartDate());
        assertThat(findReadingGroup.getEndDate().toLocalDate().toString()).isEqualTo(reqReadingGroupDto1.getEndDate());
        assertThat(findReadingGroup.getReadingGroupType().toString()).isEqualTo(reqReadingGroupDto1.getReadingGroupType());

        // 독서 모임 날짜 검증
        List<ReadingGroupDate> readingGroupDates = findReadingGroup.getReadingGroupDates();
        readingGroupDates.sort(Comparator.comparing(ReadingGroupDate::getConferenceDate));

        // 날짜 검증
        int index = 0;
        LocalDate startDate = LocalDate.parse(reqReadingGroupDto1.getStartDate());
        LocalDate endDate = LocalDate.parse(reqReadingGroupDto1.getEndDate());
        List<String> days = reqReadingGroupDto1.getDays();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1L)) {
            String baseDay = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
            for (String day : days) {
                if (day.equalsIgnoreCase(baseDay)) {
                    assertThat(readingGroupDates.get(index).getConferenceDate().toLocalDate()).isEqualTo(date);
                    ++index;
                    break;
                }
            }
        }

        // 날짜 개수 검증
        assertThat(readingGroupDates.size()).isEqualTo(index);

        // 요일 검증
        List<String> reqDays = reqReadingGroupDto1.getDays();
        reqDays.replaceAll(String::toUpperCase);
        for (ReadingGroupDate readingGroupDate : readingGroupDates) {
            String savedDay = readingGroupDate.getConferenceDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();
            assertThat(reqDays).contains(savedDay);
        }

        /**
         * 독서 모임 회원 검증
         */
        List<ReadingGroupMember> readingGroupMembers = findReadingGroup.getReadingGroupMembers();
        assertThat(readingGroupMembers.size()).isEqualTo(1);
        assertThat(readingGroupMembers.get(0).getMember().getSeq()).isEqualTo(member1.getSeq());
    }

    @DisplayName("독서 모임 모집 포스팅 상세페이지 Dto get 서비스 메서드 테스트")
    @Test
    public void readingGroupDetailDtoGetTest() {
        memberRepository.save(member1);

        ReadingGroup savedReadingGroup = readingGroupService.register(reqReadingGroupDto1, member1.getSeq());
        em.flush();
        em.clear();

        ResReadingGroupDetailDto resDto1 = readingGroupService.getResReadingGroupDetailDto(savedReadingGroup.getSeq());

        assertThat(resDto1.getReadingGroupSeq()).isEqualTo(savedReadingGroup.getSeq());
        assertThat(resDto1.getWriter()).isEqualTo(savedReadingGroup.getMember().getNickname());
        assertThat(resDto1.getTitle()).isEqualTo(reqReadingGroupDto1.getTitle());
        assertThat(resDto1.getContent()).isEqualTo(reqReadingGroupDto1.getContent());
        assertThat(resDto1.getMinLevel()).isEqualTo(reqReadingGroupDto1.getLimitLevel());
        assertThat(resDto1.getMaxNumOfMember()).isEqualTo(reqReadingGroupDto1.getMaxMember());
        assertThat(resDto1.getViews()).isEqualTo(savedReadingGroup.getViews() + 1);
        assertThat(resDto1.getStatus()).isEqualTo(savedReadingGroup.getStatus());
        assertThat(resDto1.getDeadline()).isEqualTo(reqReadingGroupDto1.getDeadline());
        assertThat(resDto1.getStartDate()).isEqualTo(reqReadingGroupDto1.getStartDate());
        assertThat(resDto1.getEndDate()).isEqualTo(reqReadingGroupDto1.getEndDate());
        assertThat(resDto1.getReadingGroupType().toString()).isEqualToIgnoringCase(reqReadingGroupDto1.getReadingGroupType());
        assertThat(resDto1.getCreatedDate()).isEqualTo(savedReadingGroup.getCreatedDate().toLocalDate());
        assertThat(resDto1.getParticipants().size()).isEqualTo(1);
        assertThat(resDto1.getParticipants().get(0)).isEqualTo(savedReadingGroup.getMember().getNickname());
        assertThat(resDto1.getDays().size()).isEqualTo(reqReadingGroupDto1.getDays().size());

        List<String> days = reqReadingGroupDto1.getDays();
        days = days.stream().map(String::toUpperCase)
                .collect(Collectors.toList());
        for (Day day : resDto1.getDays()) {
            assertThat(days).contains(day.toString());
        }
    }

}