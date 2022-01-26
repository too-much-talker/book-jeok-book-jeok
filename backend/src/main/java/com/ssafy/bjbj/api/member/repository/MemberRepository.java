package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("SELECT m FROM Member m WHERE m.seq = :seq")
    Member findBySeq(@Param("seq") Long seq);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findMemberByEmail(String email);

    Member findMemberByNickname(String nickname);

    Member findMemberBySeq(Long seq);

    @Query("SELECT m.point FROM Member m WHERE m.seq = :seq")
    Integer findPointBySeq(@Param("seq") Long seq);

    @Query("SELECT m.exp FROM Member m WHERE m.seq = :seq")
    Integer findExpBySeq(@Param("seq") Long seq);

}
