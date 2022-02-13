package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardCommentDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoardComment;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupBoardCommentException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardCommentRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

        if (readingGroupArticle == null || readingGroupArticle.isDeleted()) {
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

    @Override
    public List<ResReadingGroupBoardCommentDto> findReadingGroupBoardCommentDtos(Long readingGroupArticleSeq) {
        ReadingGroupBoard readingGroupArticle = readingGroupBoardRepository.findBySeq(readingGroupArticleSeq);

        if (readingGroupArticle == null || readingGroupArticle.isDeleted()) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        }

        return readingGroupBoardCommentRepository.findReadingGroupBoardCommentByReadingGroupBoardSeq(readingGroupArticleSeq);
    }

    @Override
    public Integer countReadingGroupBoardComments(Long readingGroupArticleSeq) {
        return readingGroupBoardCommentRepository.countReadingGroupBoardCommentsByReadingGroupBoard(readingGroupArticleSeq);
    }

    @Transactional
    @Override
    public ResReadingGroupBoardCommentDto updateReadingGroupBoardComment(Long readingGroupBoardCommentSeq, ReqReadingGroupBoardCommentDto reqReadingGroupBoardCommentDto, Long memberSeq) {

        ReadingGroupBoardComment readingGroupBoardComment = readingGroupBoardCommentRepository.findBySeq(readingGroupBoardCommentSeq);

        if (readingGroupBoardComment == null || readingGroupBoardComment.isDeleted()) {
            throw new NotFoundReadingGroupBoardCommentException("올바르지 않은 요청입니다");
        } else if (!readingGroupBoardComment.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다");
        } else {
            readingGroupBoardComment.changeReadingGroupBoardComment(reqReadingGroupBoardCommentDto.getContent());
        }

        return ResReadingGroupBoardCommentDto.builder()
                .readingGroupBoardCommentSeq(readingGroupBoardCommentSeq)
                .content(readingGroupBoardComment.getContent())
                .memberNickname(readingGroupBoardComment.getMember().getNickname())
                .createdDate(readingGroupBoardComment.getCreatedDate())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    @Transactional
    @Override
    public void deleteReadingGroupBoardComment(Long readingGroupBoardCommentSeq, Long memberSeq) {
        ReadingGroupBoardComment readingGroupBoardComment = readingGroupBoardCommentRepository.findBySeq(readingGroupBoardCommentSeq);

        if (readingGroupBoardComment == null) {
            throw new NotFoundReadingGroupBoardCommentException("올바르지 않은 요청입니다.");
        } else if (!readingGroupBoardComment.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        } else if (readingGroupBoardComment.isDeleted()) {
            throw new NotFoundReadingGroupBoardCommentException("올바르지 않은 요청입니다.");
        } else {
            readingGroupBoardComment.delete();
        }
    }
}
