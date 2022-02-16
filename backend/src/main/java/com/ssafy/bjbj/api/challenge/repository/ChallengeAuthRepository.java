package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.entity.ChallengeAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeAuthRepository extends JpaRepository<ChallengeAuth, Long>, ChallengeAuthRepositoryCustom {

    Integer countChallengeAuthByChallengeSeqAndMemberSeq(Long challengeSeq, Long memberSeq);

    boolean existsChallengeAuthByMemberSeqAndChallengeSeqAndCreatedDateBetween(Long memberSeq, Long challengeSeq, LocalDateTime start, LocalDateTime end);

}
