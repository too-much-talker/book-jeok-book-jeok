package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

}
