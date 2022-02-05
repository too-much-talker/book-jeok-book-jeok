package com.ssafy.bjbj.api.readinggroup.service;

import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.readinggroup.dto.request.ReqReadingGroupBoardDto;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupBoard;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.entity.file.FileInfo;
import com.ssafy.bjbj.common.repository.file.FileInfoRepository;
import com.ssafy.bjbj.common.service.file.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

        if (!fileInfos.isEmpty()) {
            fileInfoRepository.saveAll(fileInfos);
        }

        Member member = memberRepository.findBySeq(reqReadingGroupBoardDto.getMemberSeq());

        ReadingGroupBoard savedReadingGroupBoard = readingGroupBoardRepository.save(ReadingGroupBoard.builder()
                .title(reqReadingGroupBoardDto.getTitle())
                .content(reqReadingGroupBoardDto.getContent())
                .views(0)
                .isDeleted(false)
                .member(member)
                .readingGroup(readingGroupRepository.findBySeq(reqReadingGroupBoardDto.getReadingGroupSeq()))
                .build());

        return savedReadingGroupBoard.getSeq();
    }
}
