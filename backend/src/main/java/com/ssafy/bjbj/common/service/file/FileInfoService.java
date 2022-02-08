package com.ssafy.bjbj.common.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileInfoService {

    void registerFileInfo(Long rootSeq,List<MultipartFile> files) throws Exception;

    List<String> findAllFileInfoByReadingGroupBoardSeq(Long readingGroupBoardSeq);

    void updateFileInfo(Long rootSeq, List<MultipartFile> files) throws Exception;

    void deleteFileInfo(Long rootSeq, Long memberSeq);

}
