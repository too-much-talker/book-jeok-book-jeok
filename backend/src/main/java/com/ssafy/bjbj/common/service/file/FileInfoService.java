package com.ssafy.bjbj.common.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileInfoService {

    void register(Long rootSeq, List<MultipartFile> files) throws IOException;

    List<String> findAllFileInfoByReadingGroupBoardSeq(Long readingGroupBoardSeq);

    void update(Long rootSeq, List<MultipartFile> files) throws Exception;

    void delete(Long rootSeq, Long memberSeq);

}
