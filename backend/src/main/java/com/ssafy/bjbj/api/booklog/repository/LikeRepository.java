package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.booklog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {

    Like findByBooklogSeqAndMemberSeq(Long booklogSeq, Long memberSeq);

    void deleteAllByMemberSeq(Long memberSeq);
}
