package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupDetailDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupDate;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupDateRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupMemberRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.enums.Day;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupServiceImpl implements ReadingGroupService {

    private final ReadingGroupRepository readingGroupRepository;

    private final MemberRepository memberRepository;

    private final ReadingGroupDateRepository readingGroupDateRepository;

    private final ReadingGroupMemberRepository readingGroupMemberRepository;

    @Transactional
    @Override
    public ReadingGroup register(ReqReadingGroupDto reqReadingGroupDto, Long memberSeq) {
        String time = "T00:00:00";

        Member findMember = memberRepository.findBySeq(memberSeq);

        /**
         * 독서 모임 저장
         */
        ReadingGroup savedReadingGroup = readingGroupRepository.save(ReadingGroup.builder()
                .title(reqReadingGroupDto.getTitle())
                .content(reqReadingGroupDto.getContent())
                .limitLevel(reqReadingGroupDto.getLimitLevel())
                .maxMember(reqReadingGroupDto.getMaxMember())
                .deadline(LocalDateTime.parse(reqReadingGroupDto.getDeadline() + time))
                .startDate(LocalDateTime.parse(reqReadingGroupDto.getStartDate() + time))
                .endDate(LocalDateTime.parse(reqReadingGroupDto.getEndDate() + time))
                .readingGroupType(ReadingGroupType.valueOf(reqReadingGroupDto.getReadingGroupType().toUpperCase()))
                .member(findMember)
                .build());

        /**
         * 독서 모임 날짜 저장
         */
        LocalDate startDate = LocalDate.parse(reqReadingGroupDto.getStartDate());
        LocalDate endDate = LocalDate.parse(reqReadingGroupDto.getEndDate());
        List<String> days = reqReadingGroupDto.getDays();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1L)) {
            String baseDay = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
            for (String day : days) {
                if (day.equalsIgnoreCase(baseDay)) {
                    readingGroupDateRepository.save(ReadingGroupDate.builder()
                            .conferenceDate(LocalDateTime.of(date, LocalTime.parse("00:00:00")))
                            .readingGroup(savedReadingGroup)
                            .build());
                    break;
                }
            }
        }

        /**
         * 독서 모임 회원 저장
         */
        readingGroupMemberRepository.save(ReadingGroupMember.builder()
                .readingGroup(savedReadingGroup)
                .member(findMember)
                .build());

        return savedReadingGroup;
    }

    @Transactional
    @Override
    public ResReadingGroupDetailDto getResDto(Long readingGroupSeq) {
        ReadingGroup findReadingGroup = readingGroupRepository.findBySeq(readingGroupSeq)
                .orElseThrow(() -> new NotFoundReadingGroupException("올바르지 않은 요청입니다."));

        /**
         * 조회수 증가
         */
        findReadingGroup.incrementViews();

        /**
         * 참여자 닉네임 세팅
         */
        List<String> participants = findReadingGroup.getReadingGroupMembers()
                .stream().map(readingGroupMember -> readingGroupMember.getMember().getNickname())
                .collect(Collectors.toList());

        /**
         * 요일 정보 세팅
         */
        Set<Day> days = findReadingGroup.getReadingGroupDates()
                .stream().map(readingGroupDate -> Day.valueOf(readingGroupDate.getConferenceDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase()))
                .collect(Collectors.toSet());

        return ResReadingGroupDetailDto
                .builder()
                .readingGroupSeq(findReadingGroup.getSeq())
                .writer(findReadingGroup.getMember().getNickname())
                .title(findReadingGroup.getTitle())
                .content(findReadingGroup.getContent())
                .minLevel(findReadingGroup.getLimitLevel())
                .maxNumOfMember(findReadingGroup.getMaxMember())
                .views(findReadingGroup.getViews())
                .status(findReadingGroup.getStatus())
                .deadline(findReadingGroup.getDeadline().toLocalDate())
                .startDate(findReadingGroup.getStartDate().toLocalDate())
                .endDate(findReadingGroup.getEndDate().toLocalDate())
                .readingGroupType(findReadingGroup.getReadingGroupType())
                .createdDate(findReadingGroup.getCreatedDate().toLocalDate())
                .participants(participants)
                .days(days)
                .build();
    }

}
