package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.*;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChallengeService {

    Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq);

    ResChallengeListPageDto getResChallengeListPageDto(boolean all, Pageable pageable);

    ResChallengeDto getResChallengeDto(Long challengeSeq);

    List<ResRewardDto> getRewardDtos();

    ChallengeMember join(Long challengeSeq, Long memberSeq);

    void unJoin(Long challengeSeq, Long memberSeq);

    ResMyChallengeListPageDto getResMyChallengeListPageDto(boolean isEnd, Pageable pageable, Long memberSeq);

    Challenge modify(Long challengeSeq, Long memberSeq, ReqChallengeDto reqChallengeDto);

    void remove(Long challengeSeq, Long memberSeq);

    ResMyChallengeDetailDto getMyChallengeDetailDto(Long challengeSeq, Long memberSeq);

}