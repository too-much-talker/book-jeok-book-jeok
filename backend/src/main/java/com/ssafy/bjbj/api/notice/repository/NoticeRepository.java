package com.ssafy.bjbj.api.notice.repository;

import com.ssafy.bjbj.api.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {



}
