package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooklogRepository extends JpaRepository<Booklog, Long> {

    @Query("SELECT b FROM Booklog b WHERE b.seq = :seq")
    Booklog findBySeq(@Param("seq") Long seq);

}
