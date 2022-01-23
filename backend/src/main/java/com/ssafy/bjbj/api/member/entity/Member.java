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
import com.ssafy.bjbj.api.readinggroup.entity.ExpHistory;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.common.entity.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
@Entity
public class Member extends BaseLastModifiedEntity {

    @Column(name = "member_id")
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Integer point = 100;

    @Column(nullable = false)
    private Integer exp = 0;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    @JoinColumn(name = "booklog_id")
    @OneToMany
    private List<Booklog> booklogs = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "book_info_id", referencedColumnName = "book_info_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<BookReview> bookReviews = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "booklog_id", referencedColumnName = "booklog_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
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

    @JoinColumn(name = "activity_id")
    @OneToMany
    private List<activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @JoinColumn(name = "notice_comment_id")
    @OneToMany
    private List<NoticeComment> noticeComments = new ArrayList<>();

    @JoinColumn(name = "reading_group_id")
    @OneToMany
    private List<ReadingGroup> readingGroups = new ArrayList<>();

    @JoinColumn(name = "reading_group_board_id")
    @OneToMany
    private List<ReadingGroupBoard> readingGroupBoards = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "reading_group_id", referencedColumnName = "reading_group_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<ReadingGroupMember> readingGroupMembers = new ArrayList<>();

    @JoinColumn(name = "exp_history_id")
    @OneToMany
    private List<ExpHistory> expHistories = new ArrayList<>();

    // challenge part
    @Column(nullable = false)
    @JoinColumn(name = "challenge_id")
    @OneToMany
    private List<Challenge> challenges = new ArrayList<>();

    @Column(nullable = false)
    @JoinColumn(name = "challenge_auth_id")
    @OneToMany
    private List<ChallengeAuth> challengeAuths = new ArrayList<>();

    @JoinColumns({
            @JoinColumn(name = "challenge_id", referencedColumnName = "challenge_id"),
            @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    @OneToMany
    private List<ChallengeMember> challengeMembers = new ArrayList<>();

    @Column(nullable = false)
    @JoinColumn(name = "point_history_id")
    @OneToMany
    private List<PointHistory> pointHistories = new ArrayList<>();
}
