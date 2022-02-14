package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.service.ActivityService;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardPageDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroup;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import com.ssafy.bjbj.common.service.file.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.bjbj.api.member.enums.ActivityType.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupBoardServiceImpl implements ReadingGroupBoardService {

    private final ReadingGroupRepository readingGroupRepository;

    private final MemberRepository memberRepository;

    private final ReadingGroupBoardRepository readingGroupBoardRepository;

    private final FileInfoService fileInfoService;

    private final ActivityService activityService;

    @Transactional
    @Override
    public Long register(ReqReadingGroupBoardDto reqReadingGroupBoardDto, List<MultipartFile> files, Long memberSeq) throws IOException {
        Member findMember = memberRepository.findBySeq(memberSeq);
        ReadingGroup findReadingGroup = readingGroupRepository.findBySeq(reqReadingGroupBoardDto.getReadingGroupSeq())
                .orElseThrow(() -> new NotFoundReadingGroupException("올바르지 않은 요청입니다."));

        /**
         * 독서 모임 게시글 저장
         */
        ReadingGroupBoard savedReadingGroupBoard = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title(reqReadingGroupBoardDto.getTitle())
                .content(reqReadingGroupBoardDto.getContent())
                .member(findMember)
                .readingGroup(findReadingGroup)
                .build());

        /**
         * 파일 저장
         */
        fileInfoService.register(savedReadingGroupBoard.getSeq(), files);

        activityService.createNewActivity(savedReadingGroupBoard.getSeq(), findMember, READING_GROUP_BOARD_CREATE, savedReadingGroupBoard.getCreatedDate());

        return savedReadingGroupBoard.getSeq();
    }

    @Transactional
    @Override
    public ResReadingGroupArticleDto findReadingGroupArticleBySeq(Long readingGroupArticleSeq, Long memberSeq) {
        ReadingGroupBoard readingGroupBoard = readingGroupBoardRepository.findBySeq(readingGroupArticleSeq);

        if (readingGroupBoard == null) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        }
        if (readingGroupBoard.isDeleted()) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        }

        if (!readingGroupBoard.getMember().getSeq().equals(memberSeq)) {
            readingGroupBoard.incrementViews();
        }

        return ResReadingGroupArticleDto.builder()
                .memberSeq(readingGroupBoard.getMember().getSeq())
                .readingGroupSeq(readingGroupBoard.getReadingGroup().getSeq())
                .readingGroupBoardSeq(readingGroupArticleSeq)
                .title(readingGroupBoard.getTitle())
                .content(readingGroupBoard.getContent())
                .nickname(readingGroupBoard.getMember().getNickname())
                .createDate(readingGroupBoard.getCreatedDate())
                .modifiedDate(readingGroupBoard.getLastModifiedDate())
                .views(readingGroupBoard.getViews())
                .build();

    }

    @Override
    public ResReadingGroupBoardPageDto getResReadingGroupBoardListDto(Long readingGroupseq, Pageable pageable) {

        Integer totalCnt = readingGroupBoardRepository.countReadingGroupBoard(readingGroupseq);
        Integer totalPage = (int) Math.ceil((double)totalCnt/pageable.getPageSize());
        List<ResReadingGroupArticleDto> readingGroupDtos = readingGroupBoardRepository.findReadingGroupDtos(readingGroupseq, pageable);

        return ResReadingGroupBoardPageDto.builder()
                .totalCnt(totalCnt)
                .currentPage(pageable.getPageNumber())
                .totalPage(totalPage)
                .resReadingGroupArticleDtos(readingGroupDtos)
                .build();
    }

    @Transactional
    @Override
    public ResReadingGroupArticleDto updateReadingGroupArticleBySeq(Long readingGroupArticleSeq, Long memberSeq, ReqReadingGroupBoardDto reqReadingGroupBoardDto) {
        ReadingGroup findReadingGroup = readingGroupRepository.findBySeq(reqReadingGroupBoardDto.getReadingGroupSeq())
                .orElseThrow(() -> new NotFoundReadingGroupException("올바르지 않은 요청입니다."));
        ReadingGroupBoard readingGroupBoard = readingGroupBoardRepository.findBySeq(readingGroupArticleSeq);

        if (readingGroupBoard == null || readingGroupBoard.isDeleted()) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다");
        } else if (!readingGroupBoard.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다");
        } else {
            readingGroupBoard.changeReadingGroupArticle(reqReadingGroupBoardDto.getTitle(), reqReadingGroupBoardDto.getContent());
            ResReadingGroupArticleDto resReadingGroupArticleDto = ResReadingGroupArticleDto.builder()
                    .memberSeq(readingGroupBoard.getMember().getSeq())
                    .readingGroupSeq(findReadingGroup.getSeq())
                    .readingGroupBoardSeq(readingGroupArticleSeq)
                    .title(readingGroupBoard.getTitle())
                    .content(readingGroupBoard.getContent())
                    .nickname(readingGroupBoard.getMember().getNickname())
                    .createDate(readingGroupBoard.getCreatedDate())
                    .modifiedDate(LocalDateTime.now())
                    .views(readingGroupBoard.getViews())
                    .build();

            activityService.createNewActivity(readingGroupBoard.getSeq(), readingGroupBoard.getMember(), READING_GROUP_BOARD_UPDATE, readingGroupBoard.getLastModifiedDate());

            return resReadingGroupArticleDto;
        }
    }

    @Transactional
    @Override
    public void deleteReadingGroupArticle(Long readingGroupArticleSeq, Long memberSeq) {

        ReadingGroupBoard readingGroupBoard = readingGroupBoardRepository.findBySeq(readingGroupArticleSeq);

        if (readingGroupBoard == null) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        } else if (!readingGroupBoard.getMember().getSeq().equals(memberSeq)) {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        } else if (readingGroupBoard.isDeleted()) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        } else {
            readingGroupBoard.delete();
            activityService.createNewActivity(readingGroupBoard.getSeq(), readingGroupBoard.getMember(), READING_GROUP_BOARD_DELETE, readingGroupBoard.getLastModifiedDate());
        }
    }

}
