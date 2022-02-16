package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeAuthDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeAuth;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.exception.AlreadyAuthenticateException;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeMemberException;
import com.ssafy.bjbj.api.challenge.repository.ChallengeAuthRepository;
import com.ssafy.bjbj.api.challenge.repository.ChallengeMemberRepository;
import com.ssafy.bjbj.api.challenge.repository.ChallengeRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.common.service.file.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.*;
import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeAuthServiceImpl implements ChallengeAuthService {

    private final MemberRepository memberRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengeAuthRepository challengeAuthRepository;

    private final FileInfoService fileInfoService;

    private final ChallengeMemberRepository challengeMemberRepository;

    @Transactional
    @Override
    public ChallengeAuth authenticate(Long challengeSeq, ReqChallengeAuthDto reqChallengeAuthDto, List<MultipartFile> files, Long memberSeq) throws IOException {
        Member member = memberRepository.findBySeq(memberSeq);
        Challenge challenge = challengeRepository.findBySeq(challengeSeq);

        if (challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq) == null) {
            throw new NotFoundChallengeMemberException("챌린지에 참가한 회원이 아닙니다");
        }
        if (challenge == null) {
            throw new NotFoundChallengeException("올바르지 않은 요청입니다");
        }

        boolean alreadyAuthenticate = challengeAuthRepository.existsChallengeAuthByMemberSeqAndChallengeSeqAndCreatedDateBetween(
                memberSeq,
                challengeSeq,
                LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0)),
                LocalDateTime.now());
        if (alreadyAuthenticate) {
            throw new AlreadyAuthenticateException("이미 오늘 인증한 유저입니다");
        }

        if (files == null) {
            ChallengeAuth savedChallengeAuth = challengeAuthRepository.save(ChallengeAuth.builder()
                    .title(reqChallengeAuthDto.getTitle())
                    .content(reqChallengeAuthDto.getContent())
                    .hasFile(false)
                    .isDeleted(false)
                    .member(member)
                    .challenge(challenge)
                    .build());

            return savedChallengeAuth;
        }

        ChallengeAuth savedChallengeAuth = challengeAuthRepository.save(ChallengeAuth.builder()
                .title(reqChallengeAuthDto.getTitle())
                .content(reqChallengeAuthDto.getContent())
                .hasFile(true)
                .isDeleted(false)
                .member(member)
                .challenge(challenge)
                .build());

        fileInfoService.register(savedChallengeAuth.getSeq(), files);

        member.incrementPoint(1);

        return savedChallengeAuth;
    }

    @Override
    public Integer getRewards(Long memberSeq, Long challengeSeq) {
        Integer authCounts = challengeAuthRepository.countChallengeAuthByChallengeSeqAndMemberSeq(challengeSeq, memberSeq);
        Member member = memberRepository.findMemberBySeq(memberSeq);
        Challenge challenge = challengeRepository.findBySeq(challengeSeq);

        if (challenge == null) {
            throw new NotFoundChallengeException("올바르지 않은 요청입니다");
        }

        LocalDateTime endDate = challenge.getEndDate();
        if (endDate.isAfter(LocalDateTime.now())) {
            throw new AlreadyAuthenticateException("아직 종료 시간이 아닙니다.");
        }

        Integer days = Period.between(challenge.getStartDate().toLocalDate(), endDate.toLocalDate()).getDays();

//        int rewards = challenge.getReward() / days * authCounts;
//        member.incrementPoint(rewards);
//
//        return rewards;
        return null;
    }
}
