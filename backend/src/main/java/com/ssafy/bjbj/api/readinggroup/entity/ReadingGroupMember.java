package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ReadingGroupMemberSeq.class)
@Table(name = "tb_reading_group_Member")
@Entity
public class ReadingGroupMember {

    @JoinColumn(name = "reading_group_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private ReadingGroup readingGroup;

    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    @ManyToOne(fetch = LAZY)
    @Id
    private Member member;

    @Column(nullable = false)
    private boolean isReviewed;

    @Builder
    public ReadingGroupMember(ReadingGroup readingGroup, Member member, boolean isReviewed) {
        this.readingGroup = readingGroup;
        this.member = member;
        this.isReviewed = isReviewed;
    }

    public void review() {
        this.isReviewed = true;
    }

}
