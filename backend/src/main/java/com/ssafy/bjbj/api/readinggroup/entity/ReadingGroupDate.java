package com.ssafy.bjbj.api.readinggroup.entity;

import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_reading_group_date")
@Entity
public class ReadingGroupDate extends BaseLastModifiedEntity {

    @Column(name = "reading_group_date_id")
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDateTime conferenceDate;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(name = "reading_group_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(fetch = LAZY)
    private ReadingGroup readingGroup;

}
