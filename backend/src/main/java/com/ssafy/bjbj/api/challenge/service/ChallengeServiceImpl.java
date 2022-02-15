package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.*;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeMemberException;
import com.ssafy.bjbj.api.challenge.repository.ChallengeMemberRepository;
import com.ssafy.bjbj.api.challenge.repository.ChallengeRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.ActivityService;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.bjbj.api.member.enums.ActivityType.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final ChallengeMemberRepository challengeMemberRepository;

    private final MemberRepository memberRepository;

    private final ActivityService activityService;

    @Transactional
    @Override
    public Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq) {
        Member findMember = memberRepository.findBySeq(memberSeq);
        Challenge savedChallenge = challengeRepository.save(reqChallengeDto.toEntity(findMember));

        /**
         * 챌린지 회원 저장
         */
        challengeMemberRepository.save(ChallengeMember.create(savedChallenge, findMember));

        activityService.createNewActivity(savedChallenge.getSeq(), findMember, CHALLENGE_CREATE, savedChallenge.getCreatedDate());
        findMember.incrementPoint(1);

        return savedChallenge;
    }

    @Override
    public ResChallengeListPageDto getResChallengeListPageDto(boolean isAll, Pageable pageable) {
        Integer totalCnt = challengeRepository.countChallengeMiniDto(isAll);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<ChallengeMiniDto> challengeMiniDtos = challengeRepository.findChallengeMiniDtos(isAll, pageable);

        return ResChallengeListPageDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .challengeMiniDtos(challengeMiniDtos)
                .build();
    }

    @Transactional
    @Override
    public ResChallengeDto getResChallengeDto(Long challengeSeq) {
        Challenge findChallenge = challengeRepository.findBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("존재하지 않는 챌린지입니다."));

        findChallenge.incrementViews();

        return findChallenge.toDto();
    }

    @Transactional
    @Override
    public ChallengeMember join(Long challengeSeq, Long memberSeq) {
        Challenge findChallenge = challengeRepository.findBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("존재하지 않는 챌린지입니다."));

        Member findMember = memberRepository.findBySeq(memberSeq);

        challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .ifPresent(challengeMember -> {
                    throw new IllegalStateException("이미 신청한 챌린지입니다.");
                });

        return challengeMemberRepository.save(ChallengeMember.create(findChallenge, findMember));
    }

    @Transactional
    @Override
    public void unJoin(Long challengeSeq, Long memberSeq) {
        ChallengeMember findChallengeMember = challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .orElseThrow(() -> new NotFoundChallengeMemberException("신청하지 않은 챌린지입니다."));

        challengeMemberRepository.delete(findChallengeMember);
    }

    @Override
    public ResMyChallengeListPageDto getResMyChallengeListPageDto(boolean isEnd, Pageable pageable, Long memberSeq) {
        Integer totalCnt = challengeMemberRepository.countAllByMemberSeq(isEnd, memberSeq);
        Integer totalPage = (int) Math.ceil((double) totalCnt / pageable.getPageSize());
        List<MyChallengeDto> myChallengeDtos = challengeRepository.findMyChallengeDtos(isEnd, pageable, memberSeq);

        return ResMyChallengeListPageDto.builder()
                .totalCnt(totalCnt)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .myChallengeDtos(myChallengeDtos)
                .build();
    }

    @Transactional
    @Override
    public Challenge modify(Long challengeSeq, Long memberSeq, ReqChallengeDto reqChallengeDto) {
        Challenge findChallenge = challengeRepository.findBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("존재하지 않는 챌린지입니다."));

        if (!findChallenge.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("다른 회원의 챌린지를 수정할 수 없습니다.");
        }

        activityService.createNewActivity(findChallenge.getSeq(), findChallenge.getMember(), CHALLENGE_UPDATE, findChallenge.getLastModifiedDate());

        return findChallenge.change(reqChallengeDto);
    }

}
