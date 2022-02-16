package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long>, ChallengeMemberRepositoryCustom {

    Optional<ChallengeMember> findByChallengeSeqAndMemberSeq(Long challengerSeq, Long memberSeq);

}
