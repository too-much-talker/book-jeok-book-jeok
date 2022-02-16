package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {

    ChallengeMember findByChallengeSeqAndMemberSeq(Long challengeSeq, Long memberSeq);

}
