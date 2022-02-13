package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardCommentDto;

import java.util.List;

public interface ReadingGroupBoardCommentService {

    Long registerComment(ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Long memberSeq);

    List<ResReadingGroupBoardCommentDto> findReadingGroupBoardCommentDtos(Long readingGroupArticleSeq);

    Integer countReadingGroupBoardComments(Long readingGroupArticleSeq);

    ResReadingGroupBoardCommentDto updateReadingGroupBoardComment(Long readingGroupBoardCommentSeq, ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Long memberSeq);

    void deleteReadingGroupBoardComment(Long readingGroupBoardComment, Long memberSeq);
}
