package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReadingGroupRepository extends JpaRepository<ReadingGroup, Long> {

    @Query("SELECT rg FROM ReadingGroup rg WHERE rg.seq = :seq")
    ReadingGroup findBySeq(@Param("seq") Long seq);

}
