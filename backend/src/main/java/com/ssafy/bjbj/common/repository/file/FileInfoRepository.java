package com.ssafy.bjbj.common.repository.file;

import com.ssafy.bjbj.common.entity.file.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    List<FileInfo> findAllByRootSeq(Long rootSeq);
}
