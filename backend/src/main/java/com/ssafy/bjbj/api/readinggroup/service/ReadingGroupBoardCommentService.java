package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardCommentDto;

public interface ReadingGroupBoardCommentService {

    Long registerComment(ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Long memberSeq);


}
