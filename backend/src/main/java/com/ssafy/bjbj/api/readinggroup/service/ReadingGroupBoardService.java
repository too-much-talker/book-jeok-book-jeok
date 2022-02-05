package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.common.entity.file.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReadingGroupBoardService {

    Long register(ReqReadingGroupBoardDto reqReadingGroupBoardDto, List<MultipartFile> files) throws Exception;

}
