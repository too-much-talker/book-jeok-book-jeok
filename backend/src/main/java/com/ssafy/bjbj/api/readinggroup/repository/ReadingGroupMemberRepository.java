package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingGroupMemberRepository extends JpaRepository<ReadingGroupMember, Long> {

    Optional<ReadingGroupMember> findByReadingGroupSeqAndMemberSeq(Long readingGroupSeq, Long memberSeq);

    boolean existsByReadingGroupSeqAndMemberSeq(Long readingGroupSeq, Long memberSeq);

    void deleteAllByReadingGroupSeq(Long readingGroupSeq);

    List<ReadingGroupMember> findReadingGroupMembersByMemberSeq(Long memberSeq);

    void deleteAllByMemberSeq(Long memberSeq);
}
