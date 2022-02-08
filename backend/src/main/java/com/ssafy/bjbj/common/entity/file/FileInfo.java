package com.ssafy.bjbj.common.entity.file;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bjbj.common.entity.base.BaseLastModifiedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"rootSeq", "originFileName", "encodedFileName", "savedPath", "isDeleted"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_file_info")
@Entity
public class FileInfo extends BaseLastModifiedEntity {

    @Column(name = "file_info_seq", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue
    @Id
    private Long seq;

    @Column(name = "root_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long rootSeq;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String encodedFileName;

    @Column(nullable = false)
    private String savedPath;

    @Column(nullable = false)
    private boolean isDeleted;

    @QueryProjection
    @Builder
    public FileInfo(Long rootSeq, String originFileName, String encodedFileName, String savedPath, boolean isDeleted) {
        this.rootSeq = rootSeq;
        this.originFileName = originFileName;
        this.encodedFileName = encodedFileName;
        this.savedPath = savedPath;
        this.isDeleted = isDeleted;
    }

    public void setRootSeq(Long rootSeq) {
        this.rootSeq = rootSeq;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
