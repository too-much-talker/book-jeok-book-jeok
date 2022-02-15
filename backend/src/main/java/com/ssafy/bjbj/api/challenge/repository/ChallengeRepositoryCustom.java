package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChallengeRepositoryCustom {

    Integer countChallengeMiniDto(boolean isAll);

    List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable);

}
