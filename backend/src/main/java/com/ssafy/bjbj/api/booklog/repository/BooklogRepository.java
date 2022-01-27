package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooklogRepository extends JpaRepository<Booklog, Long> {



}
