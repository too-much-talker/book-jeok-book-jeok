package com.ssafy.bjbj.common.service.file;

import java.util.List;

public interface FileInfoService {

    List<String> findAllFileInfoByReadingGroupBoardSeq(Long readingGroupBoardSeq);
}
