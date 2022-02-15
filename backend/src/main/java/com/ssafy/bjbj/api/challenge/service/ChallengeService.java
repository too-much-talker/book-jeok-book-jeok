package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResMyChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import org.springframework.data.domain.Pageable;

public interface ChallengeService {

    Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq);

    ResChallengeListPageDto getResChallengeListPageDto(boolean all, Pageable pageable);

    ResChallengeDto getResChallengeDto(Long challengeSeq);

    ChallengeMember join(Long challengeSeq, Long memberSeq);

    void unJoin(Long challengeSeq, Long memberSeq);

    ResMyChallengeListPageDto getResMyChallengeListPageDto(boolean isEnd, Pageable pageable, Long memberSeq);

}
