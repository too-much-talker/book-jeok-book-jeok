package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BooklogRepositoryTest {

    @Autowired
    private BooklogRepository booklogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private EntityManager em;

    private Member member1, member2;

    private Booklog booklog1, booklog2;

    private BookInfo bookInfo1;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .role(Role.MEMBER)
                .build();

        member2 = Member.builder()
                .email("test2@test.com")
                .password("password2")
                .name("name2")
                .nickname("nickname")
                .phoneNumber("010-0000-0002")
                .role(Role.MEMBER)
                .build();

        bookInfo1 = BookInfo.builder()
                .isbn("isbn")
                .title("??????")
                .author("??????")
                .description("??????")
                .price(10000)
                .smallImgUrl("smallImgUrl")
                .largeImgUrl("largeImgUrl")
                .categoryId(100)
                .categoryName("???????????? ??????")
                .publisher("?????????")
                .publicationDate(LocalDateTime.now())
                .build();

        // ????????? ??????
        booklog1 = Booklog.builder()
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build();

        booklog2 = Booklog.builder()
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build();
    }

    @DisplayName("????????? ????????? ?????? ?????????")
    @Test
    public void booklog_register_test() {
        // ????????????
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        // ????????? ??????
        Booklog booklog = Booklog.builder()
                .title("????????? ??????")
                .content("????????? ??????")
                .summary("????????? ?????????")
                .starRating(4)
                .readDate(LocalDateTime.parse("2021-12-21" + "T00:00:00"))
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build();

        booklogRepository.save(booklog);
        em.flush();
        em.clear();

        // ????????? ????????? ??????
        Booklog savedBooklog = booklogRepository.findBySeq(booklog.getSeq());
        assertThat(booklog.getMember().getSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(booklog.getBookInfo().getSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(booklog.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(booklog.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(booklog.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(booklog.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(booklog.getReadDate()).isEqualTo(savedBooklog.getReadDate());
        assertThat(booklog.isOpen()).isEqualTo(savedBooklog.isOpen());
        assertThat(booklog.getViews()).isEqualTo(savedBooklog.getViews());
    }

    @DisplayName("????????? ????????? ?????? ?????????")
    @Test
    public void booklog_update_test() {
        // ????????????
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        // ????????? ??????
        Booklog booklog = Booklog.builder()
                .title("????????? ??????")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build();

        booklogRepository.save(booklog);
        em.flush();
        em.clear();

        // ????????? ??????
        Booklog findBooklog = booklogRepository.findBySeq(booklog.getSeq());
        ReqBooklogDto reqBooklogDto = ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????2")
                .content("????????? ??????2")
                .summary("????????? ?????????2")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        findBooklog.changeBooklog(reqBooklogDto);
        em.flush();
        em.clear();

        // ????????? ????????? ??????
        Booklog savedBooklog = booklogRepository.findBySeq(findBooklog.getSeq());
        assertThat(findBooklog.getMember().getSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(findBooklog.getBookInfo().getSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(findBooklog.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(findBooklog.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(findBooklog.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(findBooklog.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(findBooklog.getReadDate()).isEqualTo(savedBooklog.getReadDate());
        assertThat(findBooklog.isOpen()).isEqualTo(savedBooklog.isOpen());
        assertThat(findBooklog.getViews()).isEqualTo(savedBooklog.getViews());
    }

    @DisplayName("????????? ????????? ???????????? ?????? ?????????")
    @Test
    public void booklogEntityIsOpenChangeTest() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        booklog1.changeIsOpen(false);
        booklogRepository.save(booklog1);

        // ??????
        booklog1.changeIsOpen(true);
        em.flush();
        em.clear();

        // ??????
        Booklog changedBooklog = booklogRepository.findBySeq(booklog1.getSeq());
        assertThat(changedBooklog.isOpen()).isTrue();
    }

    @DisplayName("?????? ????????? ?????? ????????? ?????? ?????? ?????????")
    @Test
    public void recentOpenBooklogCountTest() throws InterruptedException {
        Integer count = booklogRepository.countByOpenBooklogAndRecentOneWeek();
        assertThat(count).isEqualTo(0);

        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        booklog1.changeIsOpen(false);
        booklogRepository.save(booklog1);
        em.flush();
        em.clear();
        Thread.sleep(1000);
        count = booklogRepository.countByOpenBooklogAndRecentOneWeek();
        assertThat(count).isEqualTo(0);

        booklog2.changeIsOpen(true);
        booklogRepository.save(booklog2);
        em.flush();
        em.clear();
        Thread.sleep(1000);
        count = booklogRepository.countByOpenBooklogAndRecentOneWeek();
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("?????? ????????? ?????? ????????? ?????? ?????? ?????????")
    @Test
    public void RecentOpenBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);
        bookInfoRepository.save(bookInfo1);

        Pageable pageableRecent = PageRequest.of(1, 10, Sort.Direction.ASC, "recent");
        Pageable pageableLike = PageRequest.of(1, 10, Sort.Direction.ASC, "like");
        List<OpenBooklogDto> find1 = booklogRepository.findOpenBooklogDtos(pageableRecent);
        assertThat(find1).isEmpty();

        Booklog savedBooklog1 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????3")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        // booklog1 ????????? 2???
        likeRepository.save(Like.builder()
                .booklog(savedBooklog1)
                .member(member1)
                .build());
        likeRepository.save(Like.builder()
                .booklog(savedBooklog1)
                .member(member2)
                .build());
        // booklog2 ????????? 1???
        likeRepository.save(Like.builder()
                .booklog(savedBooklog2)
                .member(member1)
                .build());
        em.flush();
        em.clear();

        // ????????? : 3 -> 2 -> 1
        List<OpenBooklogDto> find2 = booklogRepository.findOpenBooklogDtos(pageableRecent);
        assertThat(find2.size()).isEqualTo(3);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find2.get(2).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // ???????????? : 1 -> 2 -> 3
        List<OpenBooklogDto> find3 = booklogRepository.findOpenBooklogDtos(pageableLike);
        assertThat(find3.size()).isEqualTo(3);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
        assertThat(find3.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find3.get(2).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
    }

    @DisplayName("?????? ????????? ?????? ?????? ?????????")
    @Test
    public void myBooklogCountTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        Integer count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(0);

        booklogRepository.save(Booklog.builder()
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();
        Thread.sleep(1000);
        
        // ????????? ????????? 1??? ????????? ??? ?????? ????????? ?????? -> 1???
        count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(1);

        em.flush();
        em.clear();

        booklogRepository.save(Booklog.builder()
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();
        Thread.sleep(1000);
        
        // ?????? ????????? 1??? ?????? ?????? ??? ?????? ????????? ?????? -> 2???
        count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(2);

        // ?????? ????????? ?????? -> 1???
        count = booklogRepository.countMyBooklogByMemberSeq(false, member1.getSeq());
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("?????? ????????? ?????? ?????? ?????????")
    @Test
    public void myBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 10);
        List<MyBooklogDto> find1 = booklogRepository.findMyBooklogDtos(true, pageable, member1.getSeq());
        assertThat(find1).isEmpty();

        Booklog savedBooklog1 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????3")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        em.flush();
        em.clear();

        // ????????? : 3 -> 2 -> 1
        List<MyBooklogDto> find2 = booklogRepository.findMyBooklogDtos(true, pageable, member1.getSeq());
        assertThat(find2.size()).isEqualTo(3);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find2.get(2).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // ???????????? : 1 -> 2 -> 3
        List<MyBooklogDto> find3 = booklogRepository.findMyBooklogDtos(false, pageable, member1.getSeq());
        assertThat(find3.size()).isEqualTo(2);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find3.get(1).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
    }

    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    @Test
    public void searchBooklogCountTest() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        String keyword = "?????????";
        String writer = member1.getNickname();

        Integer count = booklogRepository.countSearchBooklogByKeyword(keyword, writer);
        assertThat(count).isEqualTo(0);

        booklogRepository.save(Booklog.builder()
                .title("??????????????????????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false) // ?????????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();

        // ????????? ????????? 1??? ????????? ??? ?????? ????????? ?????? -> 0???
        count = booklogRepository.countSearchBooklogByKeyword(keyword, writer);
        assertThat(count).isEqualTo(0);
        em.flush();
        em.clear();

        booklogRepository.save(Booklog.builder()
                .title("??????2")
                .content("?????????????????????????????????")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // ??????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();

        // ?????? ????????? 1??? ?????? ?????? ??? ?????? ????????? ?????? -> 1???
        count = booklogRepository.countSearchBooklogByKeyword(keyword, null);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    @Test
    public void searchBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 10);
        String keyword = "?????????";
        String writer = member1.getNickname();
        List<SearchBooklogDto> find1 = booklogRepository.findSearchBooklog(pageable, keyword, writer);
        assertThat(find1).isEmpty();

        Booklog savedBooklog1ByMember1 = booklogRepository.save(Booklog.builder()
                .title("?????????????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // ??????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2ByMember1 = booklogRepository.save(Booklog.builder()
                .title("??????2")
                .content("????????? ?????????")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // ??????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3ByMember1 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????3")
                .content("????????? ????????? ?????????")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false) // ?????????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog4ByMember2 = booklogRepository.save(Booklog.builder()
                .title("????????? ??????4")
                .content("????????? ????????? ?????????")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // ??????
                .views(0)
                .member(member2)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);
        em.flush();
        em.clear();

        // keyword = "?????????" and writer = null
        List<SearchBooklogDto> find2 = booklogRepository.findSearchBooklog(pageable, keyword, null);
        assertThat(find2.size()).isEqualTo(2);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog4ByMember2.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());

        // keyword = "?????????" and writer = member1.getNickname()
        List<SearchBooklogDto> find3 = booklogRepository.findSearchBooklog(pageable, keyword, writer);
        assertThat(find3.size()).isEqualTo(1);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());

        // keyword = null and writer = member1.getNickname()
        List<SearchBooklogDto> find4 = booklogRepository.findSearchBooklog(pageable, null, writer);
        assertThat(find4.size()).isEqualTo(2);
        assertThat(find4.get(0).getBooklogSeq()).isEqualTo(savedBooklog2ByMember1.getSeq());
        assertThat(find4.get(1).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());
    }

    @DisplayName("???????????? ????????? ?????? ?????? ?????????")
    @Test
    public void likeBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);
        booklogRepository.save(booklog1);
        Thread.sleep(1000);
        booklogRepository.save(booklog2);

        Pageable pageable = PageRequest.of(1, 10);

        // ????????? 2??? ?????????
        likeRepository.save(Like.builder()
                .booklog(booklog1)
                .member(member1)
                .build());

        likeRepository.save(Like.builder()
                .booklog(booklog2)
                .member(member1)
                .build());

        List<LikeBooklogDto> find1 = booklogRepository.findLikeBooklogDtos(pageable, member1.getSeq());
        assertThat(find1.size()).isEqualTo(2);
        assertThat(find1.get(0).getBooklogSeq()).isEqualTo(booklog2.getSeq());
        assertThat(find1.get(1).getBooklogSeq()).isEqualTo(booklog1.getSeq());

        booklog1.changeIsOpen(false);
        em.merge(booklog1);
        List<LikeBooklogDto> find2 = booklogRepository.findLikeBooklogDtos(pageable, member1.getSeq());
        assertThat(find2.size()).isEqualTo(1);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(booklog2.getSeq());
    }

    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    @Test
    public void countOpenBooklogByBookInfoTest() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        Integer count = booklogRepository.countOpenBooklogByBookInfoSeq(bookInfo1.getSeq());
        assertThat(count).isEqualTo(0);

        booklogRepository.save(Booklog.builder()
                .title("??????????????????????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false) // ?????????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();

        // ????????? ????????? 1??? ????????? ??? ?????? ????????? ?????? -> 0???
        count = booklogRepository.countOpenBooklogByBookInfoSeq(bookInfo1.getSeq());
        assertThat(count).isEqualTo(0);
        em.flush();
        em.clear();

        booklogRepository.save(Booklog.builder()
                .title("??????2")
                .content("?????????????????????????????????")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // ??????
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();

        // ?????? ????????? 1??? ?????? ?????? ??? ?????? ????????? ?????? -> 1???
        count = booklogRepository.countOpenBooklogByBookInfoSeq(bookInfo1.getSeq());
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    @Test
    public void getOpenBooklogByBookInfoTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        booklogRepository.save(booklog1);
        Thread.sleep(1000);
        booklogRepository.save(booklog2);

        Pageable pageable = PageRequest.of(1, 10);

        List<OpenBooklogByBookInfoDto> find1 = booklogRepository.findOpenBooklogByBookInfoDtos(bookInfo1.getSeq(), pageable);
        assertThat(find1.size()).isEqualTo(2);
        assertThat(find1.get(0).getBooklogSeq()).isEqualTo(booklog2.getSeq());
        assertThat(find1.get(1).getBooklogSeq()).isEqualTo(booklog1.getSeq());
    }


}