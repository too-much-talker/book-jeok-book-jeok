package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findMemberByEmail(String email);

    @Query("SELECT m.point FROM Member m WHERE m.id = :id")
    Integer findPointById(@Param("id") Long id);

}
