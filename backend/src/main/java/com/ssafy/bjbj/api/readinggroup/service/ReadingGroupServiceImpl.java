package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupDate;
import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupDateRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupServiceImpl implements ReadingGroupService {

    private final ReadingGroupRepository readingGroupRepository;

    private final MemberRepository memberRepository;

    private final ReadingGroupDateRepository readingGroupDateRepository;

    @Transactional
    @Override
    public ReadingGroup register(ReqReadingGroupDto reqReadingGroupDto, Long memberSeq) {
        String time = "T00:00:00";

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
                .member(memberRepository.findBySeq(memberSeq))
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

        return savedReadingGroup;
    }

}
