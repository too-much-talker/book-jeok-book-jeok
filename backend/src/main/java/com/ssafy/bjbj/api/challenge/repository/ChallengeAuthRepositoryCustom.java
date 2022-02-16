package com.ssafy.bjbj.api.challenge.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeAuthRepositoryCustom {

    List<LocalDateTime> findCreatedDateAllByChallengeSeqAndMemberSeq(Long challengeSeq, Long memberSeq);

}
