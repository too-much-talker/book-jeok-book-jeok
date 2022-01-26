package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeAuth;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.challenge.entity.PointHistory;
import com.ssafy.bjbj.api.notice.entity.Notice;
import com.ssafy.bjbj.api.notice.entity.NoticeComment;
import com.ssafy.bjbj.api.readinggroup.entity.*;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(of = {"id", "email", "password", "name", "nickname", "exp", "point", "role", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
@Entity
public class Member extends BaseLastModifiedEntity {

    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer exp;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<Booklog> booklogs = new ArrayList<>();
    
    // 내가 쓴 책 리뷰들
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<BookReview> bookReviews = new ArrayList<>();
    
    // 내가 like한 북로그들
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<Like> likes = new ArrayList<>();

    // 나를 구독한 사람들
    @JoinColumn(name = "to_member_id")
    @OneToMany
    private List<Subscribe> fromMembers = new ArrayList<>();

    // 내가 구독한 사람들
    @JoinColumn(name = "from_member_id")
    @OneToMany
    private List<Subscribe> toMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<NoticeComment> noticeComments = new ArrayList<>();

    // 내가 개설한 독서모임들
    @OneToMany(mappedBy = "member")
    private List<ReadingGroup> readingGroups = new ArrayList<>();

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ReadingGroupBoard> readingGroupBoards = new ArrayList<>();

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();

    // 내가 들어간 독서모임들
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ReadingGroupMember> joinedReadingGroups = new ArrayList<>();

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ExpHistory> expHistories = new ArrayList<>();

    // challenge part
    // 내가 개설한 챌린지들
    @OneToMany(mappedBy = "member")
    private List<Challenge> challenges = new ArrayList<>();

    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ChallengeAuth> challengeAuths = new ArrayList<>();

    // 내가 참여한 챌린지들
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<ChallengeMember> joinedChallenges = new ArrayList<>();

    @JoinColumn(name = "member_id", nullable = false)
    @OneToMany
    private List<PointHistory> pointHistories = new ArrayList<>();

    public void changeMember(String password, String name, String nickname, String phoneNumber) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public Member(String email, String password, String name, String nickname, String phoneNumber, Role role, Integer point, Integer exp) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.point = point;
        this.exp = exp;
    }

}
