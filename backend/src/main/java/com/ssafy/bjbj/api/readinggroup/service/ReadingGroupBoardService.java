package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardPageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReadingGroupBoardService {

    Long register(ReqReadingGroupBoardDto reqReadingGroupBoardDto, List<MultipartFile> files, Long memberSeq) throws IOException;

    ResReadingGroupArticleDto findReadingGroupArticleBySeq(Long readingGroupArticleSeq, Long memberSeq);

    ResReadingGroupBoardPageDto getResReadingGroupBoardListDto(Long readingGroupSeq, Pageable pageable);

    void deleteReadingGroupArticle(Long readingGroupArticleSeq, Long memberSeq);

    ResReadingGroupArticleDto updateReadingGroupArticleBySeq(Long readingGroupArticleSeq, Long memberSeq, ReqReadingGroupBoardDto reqReadingGroupBoardDto);

}
