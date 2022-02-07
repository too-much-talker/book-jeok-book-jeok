package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReadingGroupBoardRepository extends JpaRepository<ReadingGroupBoard, Long> {

    @Query("SELECT rgb FROM ReadingGroupBoard rgb WHERE rgb.seq = :seq")
    ReadingGroupBoard findBySeq(@Param("seq") Long seq);

}
