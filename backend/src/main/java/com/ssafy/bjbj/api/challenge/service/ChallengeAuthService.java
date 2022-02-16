package com.ssafy.bjbj.api.challenge.service;

import com.ssafy.bjbj.api.challenge.dto.request.ReqChallengeAuthDto;
import com.ssafy.bjbj.api.challenge.entity.ChallengeAuth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChallengeAuthService {

    ChallengeAuth authenticate(Long challengeSeq, ReqChallengeAuthDto reqChallengeAuthDto, List<MultipartFile> files, Long memberSeq) throws IOException;

    Integer getRewards(Long memberSeq, Long challengeSeq);
}
