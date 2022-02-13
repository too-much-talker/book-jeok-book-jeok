package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoardComment;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardCommentRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupBoardCommentServiceImpl implements ReadingGroupBoardCommentService {

    private final MemberRepository memberRepository;

    private final ReadingGroupBoardRepository readingGroupBoardRepository;

    private final ReadingGroupBoardCommentRepository readingGroupBoardCommentRepository;

    @Transactional
    @Override
    public Long registerComment(ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Long memberSeq) {
        ReadingGroupBoard readingGroupArticle = readingGroupBoardRepository.findBySeq(reqReadingGroupBoardCommentDto.getReadingGroupArticleSeq());

        if (readingGroupArticle == null) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        }
        ReadingGroupBoardComment readingGroupBoardComment = readingGroupBoardCommentRepository.save(ReadingGroupBoardComment.builder()
                .content(reqReadingGroupBoardCommentDto.getContent())
                .isDeleted(false)
                .member(memberRepository.findMemberBySeq(memberSeq))
                .readingGroupBoard(readingGroupArticle)
                .build());

        return readingGroupBoardComment.getSeq();
    }
}
