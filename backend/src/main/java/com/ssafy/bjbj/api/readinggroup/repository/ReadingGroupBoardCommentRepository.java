package com.ssafy.bjbj.api.readinggroup.repository;


import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReadingGroupBoardCommentRepository extends JpaRepository<ReadingGroupBoardComment, Long>, ReadingGroupBoardCommentRepositoryCustom {

    @Query("SELECT rgbc FROM ReadingGroupBoardComment rgbc WHERE rgbc.seq = :seq")
    ReadingGroupBoardComment findBySeq(@Param("seq") Long seq);
}
