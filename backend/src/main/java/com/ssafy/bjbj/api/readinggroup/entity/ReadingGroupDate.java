package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@ToString(of = {"seq", "conferenceDate", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_date")
@Entity
public class ReadingGroupDate extends BaseLastModifiedEntity {

    @Column(name = "reading_group_date_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(nullable = false)
    private LocalDateTime conferenceDate;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "reading_group_seq")
    @ManyToOne(fetch = LAZY)
    private ReadingGroup readingGroup;

    @Builder
    public ReadingGroupDate(LocalDateTime conferenceDate, ReadingGroup readingGroup) {
        this.conferenceDate = conferenceDate;
        this.isDeleted = false;
        this.readingGroup = readingGroup;
    }

}
