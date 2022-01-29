package com.ssafy.bjbj.api.bookinfo.repository;

import com.ssafy.bjbj.api.bookinfo.dto.response.ResponseBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class BookInfoRepositoryTest {

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("응답용 책 정보 Dto를 seq로 조회하는 repository 테스트")
    @Test
    public void findBookInfoBySeq() {

        String date = "2021-12-20T12:30:00";

        String isbn = "isbn";
        String title = "title";
        String author = "author";
        String description = "description";
        Integer price = 100;
        String smallImgUrl = "smallImgUrl";
        String largeImgUrl = "largeImgUrl";
        Integer categoryId = 101;
        String categoryName = "categoryName";
        String publisher = "publisher";
        LocalDateTime publicationDate = LocalDateTime.parse(date);
        Double starRating = 4.0;

        BookInfo bookInfo = BookInfo.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .description(description)
                .price(price)
                .smallImgUrl(smallImgUrl)
                .largeImgUrl(largeImgUrl)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .publisher(publisher)
                .publicationDate(publicationDate)
                .starRating(starRating)
                .build();

        em.persist(bookInfo);
        em.flush();
        em.clear();

        ResponseBookInfoDto savedBookInfo = bookInfoRepository.findResponseBookInfoDtoBySeq(bookInfo.getSeq());

        assertThat(bookInfo.getSeq()).isEqualTo(savedBookInfo.getSeq());
        assertThat(bookInfo.getIsbn()).isEqualTo(savedBookInfo.getIsbn());
        assertThat(bookInfo.getTitle()).isEqualTo(savedBookInfo.getTitle());
        assertThat(bookInfo.getAuthor()).isEqualTo(savedBookInfo.getAuthor());
        assertThat(bookInfo.getDescription()).isEqualTo(savedBookInfo.getDescription());
        assertThat(bookInfo.getPrice()).isEqualTo(savedBookInfo.getPrice());
        assertThat(bookInfo.getSmallImgUrl()).isEqualTo(savedBookInfo.getSmallImgUrl());
        assertThat(bookInfo.getLargeImgUrl()).isEqualTo(savedBookInfo.getLargeImgUrl());
        assertThat(bookInfo.getCategoryId()).isEqualTo(savedBookInfo.getCategoryId());
        assertThat(bookInfo.getCategoryName()).isEqualTo(savedBookInfo.getCategoryName());
        assertThat(bookInfo.getPublisher()).isEqualTo(savedBookInfo.getPublisher());
        assertThat(bookInfo.getPublicationDate()).isEqualTo(savedBookInfo.getPublicationDate());
        assertThat(bookInfo.getStarRating()).isEqualTo(savedBookInfo.getStarRating());
    }
}
