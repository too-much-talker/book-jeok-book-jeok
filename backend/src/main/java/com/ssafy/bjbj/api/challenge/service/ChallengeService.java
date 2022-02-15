package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;

public interface ChallengeService {

    Challenge save(ReqChallengeDto reqChallengeDto, Long memberSeq);

}
