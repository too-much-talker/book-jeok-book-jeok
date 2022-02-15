package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.MyChallengeDto;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepositoryCustom {

    Integer countChallengeMiniDto(boolean isAll);

    List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable);

    List<MyChallengeDto> findMyChallengeDtos(boolean isEnd, Pageable pageable, Long memberSeq);

    Optional<Challenge> findChallengeBySeq(Long challengeSeq);

}
