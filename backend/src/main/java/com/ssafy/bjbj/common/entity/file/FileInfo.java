package com.ssafy.bjbj.common.entity.file;

import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@ToString(of = {"id", "username", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_file_info")
@Entity
public class FileInfo extends BaseLastModifiedEntity {

    @Column(name = "file_info_id")
    @Id
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String encodedFileName;

    @Column(nullable = false)
    private String savedPath;

    @Column(nullable = false)
    private boolean isDeleted;

}
