package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.LoginDto;
import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findMemberByEmail(String email);

}
