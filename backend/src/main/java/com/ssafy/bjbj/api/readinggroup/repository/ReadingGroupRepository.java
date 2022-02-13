package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReadingGroupRepository extends JpaRepository<ReadingGroup, Long>, ReadingGroupRepositoryCustom {

    @Query("SELECT rg FROM ReadingGroup rg WHERE rg.seq = :seq")
    Optional<ReadingGroup> findBySeq(@Param("seq") Long seq);

    boolean existsBySeq(Long seq);

}
