package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupMemberException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupMemberRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupMemberServiceImpl implements ReadingGroupMemberService {

    private final ReadingGroupRepository readingGroupRepository;

    private final MemberRepository memberRepository;

    private final ReadingGroupMemberRepository readingGroupMemberRepository;

    @Transactional
    @Override
    public ReadingGroupMember join(Long readingGroupSeq, Long memberSeq) {
        /**
         * 이미 참여신청을 한 경우 예외를 발생시킨다.
         */
        readingGroupMemberRepository.findByReadingGroupSeqAndMemberSeq(readingGroupSeq, memberSeq)
                .ifPresent(readingGroupMember -> {
                    throw new IllegalStateException("이미 신청한 독서 모임입니다.");
                });

        /**
         * 독서 모임이 존재하지 않으면 예외를 발생시킨다.
         */
        ReadingGroup findReadingGroup = readingGroupRepository.findBySeq(readingGroupSeq)
                .orElseThrow(() -> new NotFoundReadingGroupException("올바르지 않은 요청입니다."));

        Member findMember = memberRepository.findBySeq(memberSeq);
        return readingGroupMemberRepository.save(ReadingGroupMember.builder()
                .readingGroup(findReadingGroup)
                .member(findMember)
                .build());
    }

    @Transactional
    @Override
    public void unJoin(Long readingGroupSeq, Long memberSeq) {
        /**
         * 독서 모임이 존재하지 않으면 예외를 발생시킨다.
         */
        ReadingGroupMember findReadingGroupMember = readingGroupMemberRepository.findByReadingGroupSeqAndMemberSeq(readingGroupSeq, memberSeq)
                .orElseThrow(() -> new NotFoundReadingGroupMemberException("올바르지 않은 요청입니다."));

        readingGroupMemberRepository.delete(findReadingGroupMember);
    }

}
