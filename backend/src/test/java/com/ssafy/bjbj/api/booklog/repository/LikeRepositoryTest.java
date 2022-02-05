package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BooklogRepository booklogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private EntityManager em;

    private Member member1;

    private BookInfo bookInfo1;

    private Booklog booklog1;
    private Booklog booklog2;

    @BeforeEach
    public void setUp() {
        String email = "setupEmail@bjbj.com";
        member1 = memberRepository.save(Member.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("setUpNickname")
                .phoneNumber("010-0000-0000")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        bookInfo1 = bookInfoRepository.save(BookInfo.builder()
                .isbn("isbn")
                .title("제목")
                .author("저자")
                .description("설명")
                .price(10000)
                .smallImgUrl("smallImgUrl")
                .largeImgUrl("largeImgUrl")
                .categoryId(100)
                .categoryName("카테고리 이름")
                .publisher("출판사")
                .publicationDate(LocalDateTime.now())
                .build());

        booklog1 = booklogRepository.save(Booklog.builder()
                .title("제목1")
                .content("내용1")
                .summary("한줄평1")
                .starRating(4)
                .readDate(LocalDateTime.now())
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());

        booklog2 = booklogRepository.save(Booklog.builder()
                .title("제목2")
                .content("내용2")
                .summary("한줄평2")
                .starRating(3)
                .readDate(LocalDateTime.now())
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
    }

    @DisplayName("Like 엔티티 조회 테스트")
    @Test
    public void likeEntityFindTest() {
        Like findLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(findLike).isNull();

        Like like = Like.builder()
                .booklog(booklog1)
                .member(member1)
                .build();
        likeRepository.save(like);

        em.flush();
        em.clear();

        Like savedLike = likeRepository.findByBooklogSeqAndMemberSeq(booklog1.getSeq(), member1.getSeq());
        assertThat(savedLike).isNotNull();
        assertThat(savedLike.getBooklog().getSeq()).isEqualTo(booklog1.getSeq());
        assertThat(savedLike.getMember().getSeq()).isEqualTo(member1.getSeq());
    }

    @DisplayName("내가 좋아요한 북로그 개수 테스트")
    @Test
    public void likeBooklogCountTest() {
        // 북로그 2개 좋아요
        Like like1 = Like.builder()
                .booklog(booklog1)
                .member(member1)
                .build();
        likeRepository.save(like1);
        em.flush();
        em.clear();
        Like like2 = Like.builder()
                .booklog(booklog2)
                .member(member1)
                .build();
        likeRepository.save(like2);
        em.flush();
        em.clear();

        // 좋아요 개수 확인 -> 2개
        Integer count1 = likeRepository.countByMemberSeq(member1.getSeq());
        assertThat(count1).isEqualTo(2);

        // 북로그 1개를 비공개 처리 후 개수 확인 -> 1개
        booklog2.changeIsOpen(false);
        em.merge(booklog2); // 준영속 상태?
        Integer count2 = likeRepository.countByMemberSeq(member1.getSeq());
        assertThat(count2).isEqualTo(1);
    }

}
