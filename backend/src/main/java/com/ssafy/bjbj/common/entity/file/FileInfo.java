package com.ssafy.bjbj.common.entity;

import com.ssafy.bjbj.api.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_file_info")
@Entity
public class FileInfo extends BaseLastModifiedEntity {

    @Column(name = "file_info_id")
    @GeneratedValue
    @Id
    private Long id;

    private String originFileName;

    private String encodedFileName;

    private String savedPath;

    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    private Member member;

}
