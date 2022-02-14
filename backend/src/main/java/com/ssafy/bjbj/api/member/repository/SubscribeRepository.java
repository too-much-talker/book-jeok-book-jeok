package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    void deleteAllByFromMemberSeq(Long memberSeq);
}
