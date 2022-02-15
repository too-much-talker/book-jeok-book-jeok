package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.repository.ChallengeMemberRepository;
import com.ssafy.bjbj.api.challenge.repository.ChallengeRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final ChallengeMemberRepository challengeMemberRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq) {
        Member findMember = memberRepository.findBySeq(memberSeq);
        Challenge savedChallenge = challengeRepository.save(reqChallengeDto.toEntity(findMember));

        /**
         * 챌린지 회원 저장
         */
        challengeMemberRepository.save(ChallengeMember.create(savedChallenge, findMember));

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

    @Override
    public ResChallengeDto getResChallengeDto(Long challengeSeq) {
        return challengeRepository.findResChallengeDto(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("존재하지 않는 챌린지입니다."));
    }

}
