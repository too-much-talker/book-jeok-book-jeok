package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.MyChallengeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChallengeRepositoryCustom {

    Integer countChallengeMiniDto(boolean isAll);

    List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable);

    List<MyChallengeDto> findMyChallengeDtos(boolean isEnd, Pageable pageable, Long memberSeq);

}
