package com.ssafy.bjbj.api.member.entity;

import com.ssafy.bjbj.api.bookinfo.entity.BookReview;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.challenge.entity.ChallengeAuth;
import com.ssafy.bjbj.api.challenge.entity.ChallengeMember;
import com.ssafy.bjbj.api.notice.entity.Notice;
import com.ssafy.bjbj.api.notice.entity.NoticeComment;
import com.ssafy.bjbj.api.readinggroup.entity.*;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@ToString(of = {"seq", "email", "password", "name", "nickname", "exp", "point", "role", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "nickname"),
                @UniqueConstraint(columnNames = "phoneNumber")
        }
)
@Entity
public class Member extends BaseLastModifiedEntity {

    @Column(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

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

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer point;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer exp;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Booklog> booklogs = new ArrayList<>();
    
    // ?????? ??? ??? ?????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<BookReview> bookReviews = new ArrayList<>();
    
    // ?????? like??? ????????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Like> likes = new ArrayList<>();

    // ?????? ????????? ?????????
    @OneToMany(mappedBy = "toMember", cascade = ALL)
    private List<Subscribe> fromMembers = new ArrayList<>();

    // ?????? ????????? ?????????
    @OneToMany(mappedBy = "fromMember", cascade = ALL)
    private List<Subscribe> toMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<NoticeComment> noticeComments = new ArrayList<>();

    // ?????? ????????? ???????????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ReadingGroup> readingGroups = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ReadingGroupBoard> readingGroupBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ReadingGroupBoardComment> readingGroupBoardComments = new ArrayList<>();

    // ?????? ????????? ???????????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ReadingGroupMember> joinedReadingGroups = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ExpHistory> expHistories = new ArrayList<>();

    // challenge part
    // ?????? ????????? ????????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ChallengeAuth> challengeAuths = new ArrayList<>();

    // ?????? ????????? ????????????
    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<ChallengeMember> joinedChallenges = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<PointHistory> pointHistories = new ArrayList<>();

    public void changeMember(String password, String name, String nickname, String phoneNumber) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public Member(String email, String password, String name, String nickname, String phoneNumber, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.point = 100;
        this.exp = 0;
    }

    public void incrementPoint(int point) {
        this.point += point;
    }

    public void decrementPoint(int point) {
        this.point -= point;
        if (this.point < 0) {
            this.point = 0;
        }
    }

    public void incrementExp(int exp) {
        this.exp += exp;
    }

    public void decrementExp(int exp) {
        this.exp -= exp;
        if (this.exp < 0) {
            this.exp = 0;
        }
    }

    public void delete() {
        this.isDeleted = true;
    }

}
