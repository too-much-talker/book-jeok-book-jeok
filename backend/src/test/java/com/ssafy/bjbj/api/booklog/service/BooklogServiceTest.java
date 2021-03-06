package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.*;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.MemberService;
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

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BooklogServiceTest {

    @Autowired
    private BooklogService booklogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BooklogRepository booklogRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    private BookInfo bookInfo1;

    private ReqMemberDto reqMemberDto1, reqMemberDto2;

    @BeforeEach
    public void setUp() throws InterruptedException {
        memberRepository.deleteAll();
        bookInfoRepository.deleteAll();
        em.flush();
        em.clear();

        reqMemberDto1 = ReqMemberDto.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname")
                .phoneNumber("010-0000-0001")
                .build();

        reqMemberDto2 = ReqMemberDto.builder()
                .email("test2@test.com")
                .password("password2")
                .name("name2")
                .nickname("nickname2")
                .phoneNumber("010-0000-0002")
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
    }

    @DisplayName("????????? ?????? ?????????")
    @Test
    public void booklog_register_test() {
        bookInfoRepository.save(bookInfo1);

        // ????????????
        Member savedMember = memberService.register(reqMemberDto1);

        // ????????? ??????
        ReqBooklogDto reqBooklogDto = ReqBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????")
                .content("????????? ??????")
                .summary("????????? ?????????")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();

        Booklog savedBooklog = booklogService.register(reqBooklogDto);

        // ????????? ????????? ??????
        Booklog findBooklog = booklogRepository.findBySeq(savedBooklog.getSeq());
        assertThat(reqBooklogDto.getMemberSeq()).isEqualTo(findBooklog.getMember().getSeq());
        assertThat(reqBooklogDto.getBookInfoSeq()).isEqualTo(findBooklog.getBookInfo().getSeq());
        assertThat(reqBooklogDto.getTitle()).isEqualTo(findBooklog.getTitle());
        assertThat(reqBooklogDto.getContent()).isEqualTo(findBooklog.getContent());
        assertThat(reqBooklogDto.getSummary()).isEqualTo(findBooklog.getSummary());
        assertThat(reqBooklogDto.getStarRating()).isEqualTo(findBooklog.getStarRating());
        assertThat(reqBooklogDto.getReadDate()).isEqualTo(findBooklog.getReadDate().toLocalDate().toString());
        assertThat(reqBooklogDto.getIsOpen()).isEqualTo(findBooklog.isOpen());
    }

    @DisplayName("????????? ?????? ?????????")
    @Test
    public void booklog_update_test() {
        bookInfoRepository.save(bookInfo1);

        // ????????????
        Member savedMember = memberService.register(reqMemberDto1);

        // ????????? ??????
        ReqBooklogDto reqBooklogDto1 = ReqBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .build();
        Booklog savedBooklog = booklogService.register(reqBooklogDto1);

        // ????????? ??????
        ReqBooklogDto reqBooklogDto2 = ReqBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????2")
                .content("????????? ??????2")
                .summary("????????? ?????????2")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        booklogService.update(savedBooklog.getSeq(), reqBooklogDto2);

        // ????????? ????????? ??????
        Booklog findBooklolg = booklogRepository.findBySeq(savedBooklog.getSeq());
        assertThat(reqBooklogDto2.getMemberSeq()).isEqualTo(findBooklolg.getMember().getSeq());
        assertThat(reqBooklogDto2.getBookInfoSeq()).isEqualTo(findBooklolg.getBookInfo().getSeq());
        assertThat(reqBooklogDto2.getTitle()).isEqualTo(findBooklolg.getTitle());
        assertThat(reqBooklogDto2.getContent()).isEqualTo(findBooklolg.getContent());
        assertThat(reqBooklogDto2.getSummary()).isEqualTo(findBooklolg.getSummary());
        assertThat(reqBooklogDto2.getStarRating()).isEqualTo(findBooklolg.getStarRating());
        assertThat(reqBooklogDto2.getReadDate()).isEqualTo(findBooklolg.getReadDate().toLocalDate().toString());
        assertThat(reqBooklogDto2.getIsOpen()).isEqualTo(findBooklolg.isOpen());
    }

    @DisplayName("????????? ?????? ?????????")
    @Test
    public void booklog_remove_test() {
        bookInfoRepository.save(bookInfo1);

        // ????????????
        Member savedMember = memberService.register(reqMemberDto1);

        // ????????? ??????
        ReqBooklogDto reqBooklogDto = ReqBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .build();
        Booklog savedBooklog = booklogService.register(reqBooklogDto);

        booklogService.remove(savedBooklog.getSeq(), savedMember.getSeq());

        // ?????? ??????
        Booklog deletedBooklog = booklogRepository.findBySeq(savedBooklog.getSeq());
        assertThat(deletedBooklog.isDeleted()).isTrue();
    }

    @DisplayName("????????? ???????????? ?????? ?????????")
    @Test
    public void booklogIsOpenChangeTest() {
        bookInfoRepository.save(bookInfo1);
        Member member1 = memberService.register(reqMemberDto1);

        // ????????? ??????
        ReqBooklogDto reqBooklogDto = ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????")
                .content("????????? ??????")
                .summary("????????? ?????????")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        Booklog savedBooklog = booklogService.register(reqBooklogDto);

        // ????????? ????????? ???????????? ??????
        assertThat(savedBooklog.isOpen()).isTrue();

        // ???????????? ??????
        booklogService.changeIsOpen(savedBooklog.getSeq(), member1.getSeq(), false);

        // ????????? ???????????? ??????
        Booklog changedBooklog = booklogService.findBySeq(savedBooklog.getSeq());
        assertThat(changedBooklog.isOpen()).isFalse();
    }

    @DisplayName("????????? ?????? ?????????")
    @Test
    public void booklogGetTest() {
        bookInfoRepository.save(bookInfo1);
        Member member1 = memberService.register(reqMemberDto1);

        Booklog booklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("??????1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());

        ResBooklogDto resBooklogDto1 = booklogService.getResBooklogDtoBooklog(booklog1.getSeq(), member1.getSeq());
        assertThat(resBooklogDto1).isNotNull();
        assertThat(resBooklogDto1.getBooklogSeq()).isEqualTo(booklog1.getSeq());
        assertThat(resBooklogDto1.getMemberSeq()).isEqualTo(member1.getSeq());
        assertThat(resBooklogDto1.getTitle()).isEqualTo(booklog1.getTitle());
        assertThat(resBooklogDto1.getContent()).isEqualTo(booklog1.getContent());
        assertThat(resBooklogDto1.getSummary()).isEqualTo(booklog1.getSummary());
        assertThat(resBooklogDto1.getStarRating()).isEqualTo(booklog1.getStarRating());
        assertThat(resBooklogDto1.getReadDate()).isEqualTo(booklog1.getReadDate() == null ? null : booklog1.getReadDate().toLocalDate());
        assertThat(resBooklogDto1.getIsOpen()).isEqualTo(booklog1.isOpen());
        assertThat(resBooklogDto1.getViews()).isEqualTo(booklog1.getViews());
        assertThat(resBooklogDto1.getCreatedDate()).isEqualTo(booklog1.getCreatedDate().toLocalDate());

        // ????????? ?????? ?????????
        for (int i = 1; i <= 5; i++) {
            ResBooklogDto resBooklogDto2 = booklogService.getResBooklogDtoBooklog(booklog1.getSeq(), member1.getSeq() + 1);
            assertThat(resBooklogDto2.getViews()).isEqualTo(i);
        }
    }

    @DisplayName("?????? ????????? ?????? ????????? ????????? ?????? ?????????")
    @Test
    public void recentOpenBooklogPageTest() throws InterruptedException {
        Member member1 = memberService.register(reqMemberDto1);
        Member member2 = memberService.register(reqMemberDto2);
        bookInfoRepository.save(bookInfo1);

        Pageable pageableRecent = PageRequest.of(1, 2, Sort.Direction.ASC, "recent");
        Pageable pageableLike = PageRequest.of(1, 2, Sort.Direction.ASC, "like");
        ResOpenBooklogPageDto find1 = booklogService.getResOpenBooklogListDto(pageableRecent);
        assertThat(find1.getTotalCnt()).isEqualTo(0);
        assertThat(find1.getTotalPage()).isEqualTo(0);
        assertThat(find1.getOpenBooklogDtos()).isEmpty();

        Booklog savedBooklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????3")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true)
                .build());
        Thread.sleep(1000);

        // booklog1 ????????? 2???
        likeService.like(savedBooklog1.getSeq(), member1.getSeq());
        likeService.like(savedBooklog1.getSeq(), member2.getSeq());

        // booklog2 ????????? 1???
        likeService.like(savedBooklog2.getSeq(), member1.getSeq());


        /**
         * ??? 3?????? ???????????? ???????????? 2?????? ????????????.
         */

        // ????????? : 3 -> 2 -> 1
        ResOpenBooklogPageDto find2 = booklogService.getResOpenBooklogListDto(pageableRecent);
        assertThat(find2.getTotalPage()).isEqualTo(2);
        assertThat(find2.getTotalCnt()).isEqualTo(3);
        assertThat(find2.getOpenBooklogDtos().size()).isEqualTo(2);
        assertThat(find2.getOpenBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.getOpenBooklogDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());

        // ???????????? : 1 -> 2 -> 3
        ResOpenBooklogPageDto find3 = booklogService.getResOpenBooklogListDto(pageableLike);
        assertThat(find3.getTotalPage()).isEqualTo(2);
        assertThat(find3.getTotalCnt()).isEqualTo(3);
        assertThat(find3.getOpenBooklogDtos().size()).isEqualTo(2);
        assertThat(find3.getOpenBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
        assertThat(find3.getOpenBooklogDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
    }

    @DisplayName("?????? ????????? ????????? ?????? ?????????")
    @Test
    public void myBooklogPageTest() throws InterruptedException {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 2);
        ResMyBooklogPageDto find1 = booklogService.getResMyBooklogPageDto(true, pageable, member1.getSeq());
        assertThat(find1.getTotalCnt()).isEqualTo(0);
        assertThat(find1.getTotalPage()).isEqualTo(0);
        assertThat(find1.getMyBooklogDtos()).isEmpty();

        Booklog savedBooklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????2")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????3")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(false)
                .build());
        Thread.sleep(1000);
        
        // ?????? ????????? ?????? -> ??? 3???
        ResMyBooklogPageDto find2 = booklogService.getResMyBooklogPageDto(true, pageable, member1.getSeq());
        assertThat(find2.getTotalPage()).isEqualTo(2);
        assertThat(find2.getTotalCnt()).isEqualTo(3);
        assertThat(find2.getMyBooklogDtos().size()).isEqualTo(2);
        assertThat(find2.getMyBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.getMyBooklogDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());

        // ?????? ????????? ?????? -> ??? 2???
        ResMyBooklogPageDto find3 = booklogService.getResMyBooklogPageDto(false, pageable, member1.getSeq());
        assertThat(find3.getTotalPage()).isEqualTo(1);
        assertThat(find3.getTotalCnt()).isEqualTo(2);
        assertThat(find3.getMyBooklogDtos().size()).isEqualTo(2);
        assertThat(find3.getMyBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find3.getMyBooklogDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
    }

    @DisplayName("????????? ?????? ????????? ?????? ?????????")
    @Test
    public void searchBooklogPageTest() throws InterruptedException {
        Member member1 = memberService.register(reqMemberDto1);
        Member member2 = memberService.register(reqMemberDto2);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 2);
        String keyword = "?????????";
        String writer = member1.getNickname();
        ResSearchBooklogPageDto find1 = booklogService.getResSearchBooklogPageDto(pageable, keyword, writer);
        assertThat(find1.getTotalCnt()).isEqualTo(0);
        assertThat(find1.getTotalPage()).isEqualTo(0);
        assertThat(find1.getSearchBooklogDtos()).isEmpty();

        Booklog savedBooklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true) // ??????
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member2.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("??????2")
                .content("??????2")
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true) // ??????
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????3")
                .content("??????3 ?????????")
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(false) // ?????????
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog4 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member2.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????4")
                .content("?????? ????????? 4")
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true) // ??????
                .build());

        // keyword = "?????????" and writer = null
        ResSearchBooklogPageDto find2 = booklogService.getResSearchBooklogPageDto(pageable, keyword, null);
        assertThat(find2.getTotalPage()).isEqualTo(1);
        assertThat(find2.getTotalCnt()).isEqualTo(2);
        assertThat(find2.getSearchBooklogDtos().size()).isEqualTo(2);
        assertThat(find2.getSearchBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog4.getSeq());
        assertThat(find2.getSearchBooklogDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // keyword = null and writer = member1.getNickname()
        ResSearchBooklogPageDto find3 = booklogService.getResSearchBooklogPageDto(pageable, null, writer);
        assertThat(find3.getTotalPage()).isEqualTo(1);
        assertThat(find3.getTotalCnt()).isEqualTo(1);
        assertThat(find3.getSearchBooklogDtos().size()).isEqualTo(1);
        assertThat(find3.getSearchBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // keyword = "?????????" and writer = member1.getNickname()
        ResSearchBooklogPageDto find4 = booklogService.getResSearchBooklogPageDto(pageable, keyword, writer);
        assertThat(find4.getTotalPage()).isEqualTo(1);
        assertThat(find4.getTotalCnt()).isEqualTo(1);
        assertThat(find4.getSearchBooklogDtos().size()).isEqualTo(1);
        assertThat(find4.getSearchBooklogDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
    }

    @DisplayName("???????????? ????????? ?????? ????????? ?????????")
    @Test
    public void likeBooklogPageTest() throws InterruptedException {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Booklog booklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("??????1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());
        Thread.sleep(1000);

        Booklog booklog2 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .isOpen(true)
                .content(null)
                .title("??????1")
                .readDate("2021-12-31")
                .summary(null)
                .starRating(3)
                .build());

        Pageable pageable = PageRequest.of(1, 10);

        ResLikeBooklogPageDto find1 = booklogService.getResLikeBooklogPageDto(pageable, member1.getSeq());
        assertThat(find1.getTotalCnt()).isEqualTo(0);
        assertThat(find1.getTotalPage()).isEqualTo(0);
        assertThat(find1.getLikeBooklogDtos()).isEmpty();

        // ????????? 2??? ?????????
        likeService.like(booklog1.getSeq(), member1.getSeq());
        likeService.like(booklog2.getSeq(), member1.getSeq());

        ResLikeBooklogPageDto find2 = booklogService.getResLikeBooklogPageDto(pageable, member1.getSeq());
        assertThat(find2.getTotalCnt()).isEqualTo(2);
        assertThat(find2.getTotalPage()).isEqualTo(1);
        assertThat(find2.getLikeBooklogDtos().get(0).getBooklogSeq()).isEqualTo(booklog2.getSeq());
        assertThat(find2.getLikeBooklogDtos().get(1).getBooklogSeq()).isEqualTo(booklog1.getSeq());

        // ????????? 1??? ????????? ??????
        booklogService.changeIsOpen(booklog1.getSeq(), member1.getSeq(), false);
        ResLikeBooklogPageDto find3 = booklogService.getResLikeBooklogPageDto(pageable, member1.getSeq());
        assertThat(find3.getTotalCnt()).isEqualTo(1);
        assertThat(find3.getTotalPage()).isEqualTo(1);
        assertThat(find3.getLikeBooklogDtos().get(0).getBooklogSeq()).isEqualTo(booklog2.getSeq());
    }

    @DisplayName("????????? ????????? ?????? ????????? ?????? ?????????")
    @Test
    public void getOpenBooklogPageByBookInfoTest() throws InterruptedException {
        Member member1 = memberService.register(reqMemberDto1);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 2);
        ResOpenBooklogPageByBookInfoDto find1 = booklogService.getResOpenBooklogPageByBookInfoDto(bookInfo1.getSeq(), pageable);
        assertThat(find1.getTotalCnt()).isEqualTo(0);
        assertThat(find1.getTotalPage()).isEqualTo(0);
        assertThat(find1.getOpenBooklogByBookInfoDtos()).isEmpty();

        Booklog savedBooklog1 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("????????? ??????1")
                .content(null)
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true) // ??????
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2 = booklogService.register(ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("??????2")
                .content("??????2")
                .summary(null)
                .starRating(1)
                .readDate("2022-01-01")
                .isOpen(true) // ??????
                .build());

        // keyword = "?????????" and writer = null
        ResOpenBooklogPageByBookInfoDto find2 = booklogService.getResOpenBooklogPageByBookInfoDto(bookInfo1.getSeq(), pageable);
        assertThat(find2.getTotalPage()).isEqualTo(1);
        assertThat(find2.getTotalCnt()).isEqualTo(2);
        assertThat(find2.getOpenBooklogByBookInfoDtos().size()).isEqualTo(2);
        assertThat(find2.getOpenBooklogByBookInfoDtos().get(0).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find2.getOpenBooklogByBookInfoDtos().get(1).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
    }

}