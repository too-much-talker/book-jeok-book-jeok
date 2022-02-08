package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.ReqBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.LikeBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.MyBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.OpenBooklogDto;
import com.ssafy.bjbj.api.booklog.dto.response.SearchBooklogDto;
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

        member1 = Member.builder()
                .email("test1@test.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .phoneNumber("010-0000-0001")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build();

        member2 = Member.builder()
                .email("test2@test.com")
                .password("password2")
                .name("name2")
                .nickname("nickname")
                .phoneNumber("010-0000-0002")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build();

        bookInfo1 = BookInfo.builder()
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
                .build();

        // 북로그 작성
        booklog1 = Booklog.builder()
                .title("북로그 제목1")
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
                .title("북로그 제목2")
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

    @DisplayName("북로그 엔티티 등록 테스트")
    @Test
    public void booklog_register_test() {
        // 회원가입
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        // 북로그 작성
        Booklog booklog = Booklog.builder()
                .title("북로그 제목")
                .content("북로그 내용")
                .summary("북로그 한줄평")
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

        // 작성된 북로그 검증
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

    @DisplayName("북로그 엔티티 수정 테스트")
    @Test
    public void booklog_update_test() {
        // 회원가입
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        // 북로그 작성
        Booklog booklog = Booklog.builder()
                .title("북로그 제목")
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

        // 북로그 수정
        Booklog findBooklog = booklogRepository.findBySeq(booklog.getSeq());
        ReqBooklogDto reqBooklogDto = ReqBooklogDto.builder()
                .memberSeq(member1.getSeq())
                .bookInfoSeq(bookInfo1.getSeq())
                .title("북로그 제목2")
                .content("북로그 내용2")
                .summary("북로그 한줄평2")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        findBooklog.changeBooklog(reqBooklogDto);
        em.flush();
        em.clear();

        // 수정된 북로그 검증
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

    @DisplayName("북로그 엔티티 공개여부 수정 테스트")
    @Test
    public void booklogEntityIsOpenChangeTest() {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        booklog1.changeIsOpen(false);
        booklogRepository.save(booklog1);

        // 수정
        booklog1.changeIsOpen(true);
        em.flush();
        em.clear();

        // 검증
        Booklog changedBooklog = booklogRepository.findBySeq(booklog1.getSeq());
        assertThat(changedBooklog.isOpen()).isTrue();
    }

    @DisplayName("최근 일주일 공개 북로그 개수 조회 테스트")
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

    @DisplayName("최근 일주일 공개 북로그 목록 조회 테스트")
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
                .title("북로그 제목1")
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
                .title("북로그 제목2")
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
                .title("북로그 제목3")
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

        // booklog1 좋아요 2개
        likeRepository.save(Like.builder()
                .booklog(savedBooklog1)
                .member(member1)
                .build());
        likeRepository.save(Like.builder()
                .booklog(savedBooklog1)
                .member(member2)
                .build());
        // booklog2 좋아요 1개
        likeRepository.save(Like.builder()
                .booklog(savedBooklog2)
                .member(member1)
                .build());
        em.flush();
        em.clear();

        // 최신순 : 3 -> 2 -> 1
        List<OpenBooklogDto> find2 = booklogRepository.findOpenBooklogDtos(pageableRecent);
        assertThat(find2.size()).isEqualTo(3);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find2.get(2).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // 좋아요순 : 1 -> 2 -> 3
        List<OpenBooklogDto> find3 = booklogRepository.findOpenBooklogDtos(pageableLike);
        assertThat(find3.size()).isEqualTo(3);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
        assertThat(find3.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find3.get(2).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
    }

    @DisplayName("나의 북로그 개수 조회 테스트")
    @Test
    public void myBooklogCountTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        Integer count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(0);

        booklogRepository.save(Booklog.builder()
                .title("북로그 제목1")
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
        
        // 비공개 북로그 1개 등록한 후 모든 북로그 조회 -> 1개
        count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(1);

        em.flush();
        em.clear();

        booklogRepository.save(Booklog.builder()
                .title("북로그 제목2")
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
        
        // 공개 북로그 1개 추가 등록 후 모든 북로그 조회 -> 2개
        count = booklogRepository.countMyBooklogByMemberSeq(true, member1.getSeq());
        assertThat(count).isEqualTo(2);

        // 공개 북로그 조회 -> 1개
        count = booklogRepository.countMyBooklogByMemberSeq(false, member1.getSeq());
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("나의 북로그 목록 조회 테스트")
    @Test
    public void myBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 10);
        List<MyBooklogDto> find1 = booklogRepository.findMyBooklogDtos(true, pageable, member1.getSeq());
        assertThat(find1).isEmpty();

        Booklog savedBooklog1 = booklogRepository.save(Booklog.builder()
                .title("북로그 제목1")
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
                .title("북로그 제목2")
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
                .title("북로그 제목3")
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

        // 최신순 : 3 -> 2 -> 1
        List<MyBooklogDto> find2 = booklogRepository.findMyBooklogDtos(true, pageable, member1.getSeq());
        assertThat(find2.size()).isEqualTo(3);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog3.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find2.get(2).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());

        // 좋아요순 : 1 -> 2 -> 3
        List<MyBooklogDto> find3 = booklogRepository.findMyBooklogDtos(false, pageable, member1.getSeq());
        assertThat(find3.size()).isEqualTo(2);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog2.getSeq());
        assertThat(find3.get(1).getBooklogSeq()).isEqualTo(savedBooklog1.getSeq());
    }

    @DisplayName("검색된 북로그 개수 조회 테스트")
    @Test
    public void searchBooklogCountTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);

        String keyword = "북로그";
        String writer = "member1";

        Integer count = booklogRepository.countSearchBooklogByKeyword(keyword, writer);
        assertThat(count).isEqualTo(0);

        booklogRepository.save(Booklog.builder()
                .title("ㅇㅇㅇㅇ북로그ㅇㅇ 제목1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false) // 비공개
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();
        Thread.sleep(1000);

        // 비공개 북로그 1개 등록한 후 공개 북로그 조회 -> 0개
        count = booklogRepository.countSearchBooklogByKeyword(keyword, writer);
        assertThat(count).isEqualTo(0);
        em.flush();
        em.clear();

        booklogRepository.save(Booklog.builder()
                .title("제목2")
                .content("ㅋㅋㅋㅋ북로그ㅋㅋㅋㅋ")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // 공개
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        em.flush();
        em.clear();
        Thread.sleep(1000);

        // 공개 북로그 1개 추가 등록 후 공개 북로그 조회 -> 1개
        count = booklogRepository.countSearchBooklogByKeyword(keyword, null);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("검색된 북로그 목록 조회 테스트")
    @Test
    public void searchBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        memberRepository.save(member2);
        bookInfoRepository.save(bookInfo1);

        Pageable pageable = PageRequest.of(1, 10);
        String keyword = "북로그";
        String writer = member1.getNickname();
        List<SearchBooklogDto> find1 = booklogRepository.findSearchBooklog(pageable, keyword, writer);
        assertThat(find1).isEmpty();

        Booklog savedBooklog1ByMember1 = booklogRepository.save(Booklog.builder()
                .title("ㅇㅇㅇ북로그 제목1")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // 공개
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog2ByMember1 = booklogRepository.save(Booklog.builder()
                .title("제목2")
                .content("본문에 ㅁㅁㅁ")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // 공개
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog3ByMember1 = booklogRepository.save(Booklog.builder()
                .title("북로그 제목3")
                .content("본문에 북로그 ㅋㅋㅋ")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false) // 비공개
                .views(0)
                .member(member1)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);

        Booklog savedBooklog4ByMember2 = booklogRepository.save(Booklog.builder()
                .title("북로그 제목4")
                .content("본문에 북로그 ㅋㅋㅋ")
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true) // 공개
                .views(0)
                .member(member2)
                .bookInfo(bookInfo1)
                .build());
        Thread.sleep(1000);
        em.flush();
        em.clear();

        // keyword = "북로그" and writer = null
        List<SearchBooklogDto> find2 = booklogRepository.findSearchBooklog(pageable, keyword, null);
        assertThat(find2.size()).isEqualTo(2);
        assertThat(find2.get(0).getBooklogSeq()).isEqualTo(savedBooklog4ByMember2.getSeq());
        assertThat(find2.get(1).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());

        // keyword = "북로그" and writer = member1.getNickname()
        List<SearchBooklogDto> find3 = booklogRepository.findSearchBooklog(pageable, keyword, writer);
        assertThat(find3.size()).isEqualTo(1);
        assertThat(find3.get(0).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());

        // keyword = null and writer = member1.getNickname()
        List<SearchBooklogDto> find4 = booklogRepository.findSearchBooklog(pageable, null, writer);
        assertThat(find4.size()).isEqualTo(2);
        assertThat(find4.get(0).getBooklogSeq()).isEqualTo(savedBooklog2ByMember1.getSeq());
        assertThat(find4.get(1).getBooklogSeq()).isEqualTo(savedBooklog1ByMember1.getSeq());
    }

    @DisplayName("좋아요한 북로그 목록 반환 테스트")
    @Test
    public void likeBooklogListTest() throws InterruptedException {
        memberRepository.save(member1);
        bookInfoRepository.save(bookInfo1);
        booklogRepository.save(booklog1);
        Thread.sleep(1000);
        booklogRepository.save(booklog2);

        Pageable pageable = PageRequest.of(1, 10);

        // 북로그 2개 좋아요
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


}