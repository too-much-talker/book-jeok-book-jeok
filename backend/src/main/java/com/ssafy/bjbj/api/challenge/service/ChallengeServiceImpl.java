package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResRewardDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.challenge.exception.NotFoundChallengeException;
import com.ssafy.bjbj.api.challenge.repository.ChallengeAuthRepository;
import com.ssafy.bjbj.api.challenge.dto.response.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.bjbj.api.member.enums.ActivityType.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    private final ChallengeMemberRepository challengeMemberRepository;

    private final MemberRepository memberRepository;

    private final ChallengeAuthRepository challengeAuthRepository;

    private final ActivityService activityService;

    @Transactional
    @Override
    public Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq) {
        Member findMember = memberRepository.findBySeq(memberSeq);
        Challenge savedChallenge = challengeRepository.save(reqChallengeDto.toEntity(findMember));

        /**
         * ????????? ?????? ??????
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
        Challenge findChallenge = challengeRepository.findChallengeBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("???????????? ?????? ??????????????????."));

        findChallenge.incrementViews();

        List<Long> participantSeqs = findChallenge.getChallengeMembers()
                .stream().map(challengeMember -> challengeMember.getMember().getSeq())
                .collect(Collectors.toList());

        List<String> participantNicknames = findChallenge.getChallengeMembers()
                .stream().map(challengeMember -> challengeMember.getMember().getNickname())
                .collect(Collectors.toList());

        return findChallenge.toDto(participantSeqs, participantNicknames);
    }

    @Transactional
    @Override
    public ChallengeMember join(Long challengeSeq, Long memberSeq) {
        Challenge findChallenge = challengeRepository.findChallengeBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("???????????? ?????? ??????????????????."));

        Member findMember = memberRepository.findBySeq(memberSeq);

        challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .ifPresent(challengeMember -> {
                    throw new IllegalStateException("?????? ????????? ??????????????????.");
                });

        return challengeMemberRepository.save(ChallengeMember.create(findChallenge, findMember));
    }

    @Transactional
    @Override
    public void unJoin(Long challengeSeq, Long memberSeq) {
        ChallengeMember findChallengeMember = challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .orElseThrow(() -> new NotFoundChallengeMemberException("???????????? ?????? ??????????????????."));

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
        Challenge findChallenge = challengeRepository.findChallengeBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("???????????? ?????? ??????????????????."));

        if (!findChallenge.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("?????? ????????? ???????????? ????????? ??? ????????????.");
        }

        activityService.createNewActivity(findChallenge.getSeq(), findChallenge.getMember(), CHALLENGE_UPDATE, findChallenge.getLastModifiedDate());

        return findChallenge.change(reqChallengeDto);
    }

    @Transactional
    @Override
    public void remove(Long challengeSeq, Long memberSeq) {
        Challenge findChallenge = challengeRepository.findChallengeBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("???????????? ?????? ??????????????????."));

        if (!findChallenge.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("?????? ????????? ???????????? ????????? ??? ????????????.");
        }

        findChallenge.delete();

        activityService.createNewActivity(findChallenge.getSeq(), findChallenge.getMember(), CHALLENGE_DELETE, findChallenge.getLastModifiedDate());
        findChallenge.getMember().decrementPoint(1);
    }

    @Transactional
    @Override
    public List<ResRewardDto> getRewardDtos() {
        List<ResRewardDto> resRewardDtos = new ArrayList<>();
        List<Challenge> endedChallenges = challengeRepository.findEndedChallenge();

        if (endedChallenges == null) {
            return resRewardDtos;
        }

        for (Challenge endedChallenge : endedChallenges) {
//            Challenge challenge = challengeRepository.findBySeq(endedChallenge.getChallengeSeq());
            for (ChallengeMember challengeMember : endedChallenge.getChallengeMembers()) {
                Member member = challengeMember.getMember();
                Integer authCounts = challengeAuthRepository.countChallengeAuthByChallengeSeqAndMemberSeq(endedChallenge.getSeq(), member.getSeq());
                long days = ChronoUnit.DAYS.between(endedChallenge.getStartDate(), endedChallenge.getEndDate());

                int rewards;
                if ((float) (authCounts / days) >= 0.8) {
                    rewards = (int) (endedChallenge.getReward() / days * authCounts * 1.2);
                } else {
                    rewards = (int) (endedChallenge.getReward() / days * authCounts);
                }
                member.incrementPoint(rewards);
                challengeMember.inclementReward(rewards);

                resRewardDtos.add(ResRewardDto.builder()
                        .challengeSeq(endedChallenge.getSeq())
                        .memberSeq(member.getSeq())
                        .rewards(rewards)
                        .build());
            }
            endedChallenge.rewarded();
        }

        return resRewardDtos;
    }

    @Transactional
    @Override
    public ResMyChallengeDetailDto getMyChallengeDetailDto(Long challengeSeq, Long memberSeq) {
        Challenge findChallenge = challengeRepository.findChallengeBySeq(challengeSeq)
                .orElseThrow(() -> new NotFoundChallengeException("???????????? ?????? ??????????????????."));

        challengeMemberRepository.findByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .orElseThrow(() -> new NotFoundChallengeMemberException("???????????? ?????? ?????????????????????."));

        findChallenge.incrementViews();

        List<LocalDate> authDates = challengeAuthRepository.findCreatedDateAllByChallengeSeqAndMemberSeq(challengeSeq, memberSeq)
                .stream().map(LocalDateTime::toLocalDate)
                .collect(Collectors.toList());

        boolean isTodayAuth = false;
        for (LocalDate authDate : authDates) {
            if (authDate.equals(LocalDate.now())) {
                isTodayAuth = true;
                break;
            }
        }

        List<Long> participantSeqs = findChallenge.getChallengeMembers()
                .stream().map(challengeMember -> challengeMember.getMember().getSeq())
                .collect(Collectors.toList());

        List<String> participantNicknames = findChallenge.getChallengeMembers()
                .stream().map(challengeMember -> challengeMember.getMember().getNickname())
                .collect(Collectors.toList());

        double authRate = challengeRepository.findAuthRateByMemberSeq(challengeSeq, memberSeq);

        return ResMyChallengeDetailDto.builder()
                .challengeSeq(findChallenge.getSeq())
                .writerSeq(findChallenge.getMember().getSeq())
                .title(findChallenge.getTitle())
                .content(findChallenge.getContent())
                .startDate(findChallenge.getStartDate().toLocalDate())
                .endDate(findChallenge.getEndDate().toLocalDate())
                .deadline(findChallenge.getDeadline().toLocalDate())
                .reward(findChallenge.getReward())
                .maxMember(findChallenge.getMaxMember())
                .views(findChallenge.getViews())
                .isTodayAuth(isTodayAuth)
                .authRate(authRate)
                .participantSeqs(participantSeqs)
                .participantNicknames(participantNicknames)
                .authDates(authDates)
                .build();
    }

}
