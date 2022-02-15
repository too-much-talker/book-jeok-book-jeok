package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.ResChallengeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepositoryCustom {

    Integer countChallengeMiniDto(boolean isAll);

    List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable);

    Optional<ResChallengeDto> findResChallengeDto(Long challengeSeq);

}
