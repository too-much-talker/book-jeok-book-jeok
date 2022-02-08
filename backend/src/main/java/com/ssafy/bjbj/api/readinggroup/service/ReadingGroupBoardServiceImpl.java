package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupArticleDto;
import com.ssafy.bjbj.api.readinggroup.dto.response.ResReadingGroupBoardPageDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.exception.NotFoundReadingGroupArticleException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.entity.file.FileInfo;
import com.ssafy.bjbj.common.repository.file.FileInfoRepository;
import com.ssafy.bjbj.common.service.file.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingGroupBoardServiceImpl implements ReadingGroupBoardService {

    private final ReadingGroupRepository readingGroupRepository;

    private final FileInfoRepository fileInfoRepository;

    private final MemberRepository memberRepository;

    private final ReadingGroupBoardRepository readingGroupBoardRepository;

    private final FileHandler fileHandler;

    @Transactional
    @Override
    public Long register(ReqReadingGroupBoardDto reqReadingGroupBoardDto, List<MultipartFile> files) throws IOException {

        List<FileInfo> fileInfos = fileHandler.parseFileInfo(files);

        Member member = memberRepository.findBySeq(reqReadingGroupBoardDto.getMemberSeq());

        ReadingGroupBoard savedReadingGroupBoard = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title(reqReadingGroupBoardDto.getTitle())
                .content(reqReadingGroupBoardDto.getContent())
                .views(0)
                .isDeleted(false)
                .member(member)
                .readingGroup(readingGroupRepository.findBySeq(reqReadingGroupBoardDto.getReadingGroupSeq()))
                .build());

        if (!fileInfos.isEmpty()) {
            for (FileInfo fileInfo : fileInfos) {
                fileInfo.setRootSeq(savedReadingGroupBoard.getSeq());
                fileInfoRepository.save(fileInfo);
            }
        }

        return savedReadingGroupBoard.getSeq();
    }

    @Override
    public ResReadingGroupArticleDto findReadingGroupArticleBySeq(Long readingGroupArticleSeq, Long memberSeq) {
        ReadingGroupBoard readingGroupBoard = readingGroupBoardRepository.findBySeq(readingGroupArticleSeq);

        if (readingGroupBoard == null) {
            throw new NotFoundReadingGroupArticleException("올바르지 않은 요청입니다.");
        }

        if (!readingGroupBoard.getMember().getSeq().equals(memberSeq)) {
            readingGroupBoard.incrementViews();
        }

        return ResReadingGroupArticleDto.builder()
                .memberSeq(readingGroupBoard.getMember().getSeq())
                .readingGroupSeq(readingGroupArticleSeq)
                .readingGroupBoardSeq(readingGroupArticleSeq)
                .title(readingGroupBoard.getTitle())
                .content(readingGroupBoard.getContent())
                .nickname(readingGroupBoard.getMember().getNickname())
                .createDate(readingGroupBoard.getCreatedDate())
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
}
