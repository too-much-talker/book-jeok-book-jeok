package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long>, BookInfoRepositoryCustom {

    @Query("SELECT b FROM BookInfo b WHERE b.seq = :seq")
    BookInfo findBySeq(@Param("seq") Long seq);

    Integer countByTitleLike(String title);

    Integer countByAuthorLike(String author);

    Integer countByPublisherLike(String publisher);

    Integer countByTitleLikeOrAuthorLikeOrPublisherLike(String title, String author, String publisher);

}
