package com.ssafy.bjbj.api.bookinfo.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookListDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.*;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.round;
import static com.ssafy.bjbj.api.bookinfo.entity.QBookInfo.bookInfo;
import static com.ssafy.bjbj.api.bookinfo.entity.QBookReview.bookReview;

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
                        bookInfo.publicationDate))
                .from(bookInfo)
                .where(bookInfo.seq.eq(seq))
                .fetchOne();
    }

    @Override
    public List<ResBookInfoSmallDto> findListByRequest(ReqBookListDto reqBookInfoDto) {

        long limit = reqBookInfoDto.getLimit();
        long offset = (reqBookInfoDto.getPage() - 1) * limit;

        BooleanExpression baseExpression = bookReview.isDeleted.eq(false).or(bookReview.isDeleted.isNull());

        JPAQuery<ResBookInfoSmallDto> query = queryFactory.select(new QResBookInfoSmallDto(
                        bookInfo.seq,
                        bookInfo.title,
                        bookInfo.author,
                        bookInfo.largeImgUrl,
                        bookInfo.publisher,
                        bookInfo.publicationDate,
                        round(bookReview.starRating.avg(), 2).coalesce(0.0).as("star"),
                        bookReview.seq.count().as("reviewCnt")
                ))
                .from(bookInfo)
                .leftJoin(bookReview).on(bookReview.bookInfo.eq(bookInfo));

        // 검색 키워드가 없으면 검색 안함
        if (reqBookInfoDto.getSearchKeyword().isBlank()) {
            query.where(baseExpression);
        } else {
            // 검색 키워드가 있을 시 검색 카테고리에 맞춰 검색
            BooleanExpression search = search(reqBookInfoDto.getSearchCategory(), "%" + reqBookInfoDto.getSearchKeyword().trim() + "%");
            query.where(search.and(baseExpression));
        }
        query.groupBy(bookInfo.seq)
                // 정렬
                .orderBy(order(reqBookInfoDto.getOrderCategory()), bookInfo.publicationDate.desc())
                .limit(limit)
                .offset(offset);

        return query.fetch();
    }

    private BooleanExpression search(String searchCategory, String searchKeyword) {

        if (searchCategory.equals("title")) {
            return bookInfo.title.like(searchKeyword);
        } else if (searchCategory.equals("author")) {
            return bookInfo.author.like(searchKeyword);
        } else if (searchCategory.equals("publisher")) {
            return bookInfo.publisher.like(searchKeyword);
        } else {    // searchCategory.equals("total")       // default
            return bookInfo.title.like(searchKeyword).or(bookInfo.author.like(searchKeyword)).or(bookInfo.publisher.like(searchKeyword));
        }
    }

    private OrderSpecifier order(String orderCategory) {

        if (orderCategory.equals("review")) {
            return bookReview.seq.count().desc();
        } else if (orderCategory.equals("star")) {
            return round(bookReview.starRating.avg(), 2).coalesce(0.0).desc();
        } else {    // orderCategory.equals("latest")       // default
            return bookInfo.publicationDate.desc();
        }
    }
}
