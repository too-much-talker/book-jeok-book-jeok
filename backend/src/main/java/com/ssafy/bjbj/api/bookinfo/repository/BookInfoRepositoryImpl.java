package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.response.QResponseBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;

import javax.persistence.EntityManager;

import static com.ssafy.bjbj.api.bookinfo.entity.QBookInfo.bookInfo;

public class BookInfoRepositoryImpl implements BookInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookInfoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public ResponseBookInfoDto findResponseBookInfoDtoBySeq(Long seq) {
        return queryFactory
                .select(new QResponseBookInfoDto(
                        bookInfo.seq,
                        bookInfo.isbn,
                        bookInfo.title,
                        bookInfo.author,
                        bookInfo.description,
                        bookInfo.price,
                        bookInfo.smallImgUrl,
                        bookInfo.largeImgUrl,
                        bookInfo.categoryId,
                        bookInfo.categoryName,
                        bookInfo.publisher,
                        bookInfo.publicationDate
                ))
                .from(bookInfo)
                .where(bookInfo.seq.eq(seq))
                .fetchOne();
    }
}
