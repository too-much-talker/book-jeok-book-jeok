package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Long>, BookReviewRepositoryCustom {

    @Query("SELECT br FROM BookReview br WHERE br.seq = :seq")
    BookReview findBySeq(@Param("seq") Long seq);
}