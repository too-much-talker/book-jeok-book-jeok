package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReadingGroupDateRepository extends JpaRepository<ReadingGroupDate, Long> {

    boolean existsByReadingGroupSeqAndConferenceDate(Long readingGroup_seq, LocalDateTime conferenceDate);

    void deleteAllByReadingGroupSeq(Long readingGroupSeq);

}
