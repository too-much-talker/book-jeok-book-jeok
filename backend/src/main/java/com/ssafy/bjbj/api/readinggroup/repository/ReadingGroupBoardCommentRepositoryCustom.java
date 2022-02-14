package com.ssafy.bjbj.api.readinggroup.repository;

import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardCommentDto;

import java.util.List;

public interface ReadingGroupBoardCommentRepositoryCustom {

    Integer countReadingGroupBoardCommentsByReadingGroupBoard(Long readingGroupArticleSeq);

    List<ResReadingGroupBoardCommentDto> findReadingGroupBoardCommentByReadingGroupBoardSeq(Long readingGroupArticleSeq);

    long deleteAllByReadingGroupSeq(Long readingGroupSeq);

    long deleteAllByMemberSeq(Long memberSeq);
}
