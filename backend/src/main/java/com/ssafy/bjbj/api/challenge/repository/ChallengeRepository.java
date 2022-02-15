package com.ssafy.bjbj.api.challenge.repository;

import com.ssafy.bjbj.api.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeRepositoryCustom {

    Optional<Challenge> findBySeq(Long seq);

}
