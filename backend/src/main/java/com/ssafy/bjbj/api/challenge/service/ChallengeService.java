package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeListPageDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import org.springframework.data.domain.Pageable;

public interface ChallengeService {

    Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq);

    ResChallengeListPageDto getResChallengeListPageDto(boolean all, Pageable pageable);

    ResChallengeDto getResChallengeDto(Long challengeSeq);

}
