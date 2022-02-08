package com.ssafy.bjbj.api.bookinfo.service;

import com.ssafy.bjbj.api.bookinfo.dto.request.ReqBookSearchDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoSmallDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookListDto;
import com.ssafy.bjbj.api.bookinfo.dto.response.ResBookInfoDto;
import com.ssafy.bjbj.api.bookinfo.exception.NotFoundBookInfoException;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BookInfoServiceImpl implements BookInfoService {

    private final BookInfoRepository bookInfoRepository;

    @Override
    public ResBookInfoDto findResBookInfoDtoBySeq(Long seq) {
        ResBookInfoDto resBookInfoDto = bookInfoRepository.findResBookInfoDtoBySeq(seq);
        if (resBookInfoDto == null) {
            throw new NotFoundBookInfoException("올바르지 않은 요청입니다.");
        }

        return resBookInfoDto;
    }

    @Override
    public ResBookListDto findResBookSearchDto(ReqBookSearchDto reqBookInfoDto) {

        List<ResBookInfoSmallDto> listByRequest = bookInfoRepository.findListByRequest(reqBookInfoDto);
        String keyword = reqBookInfoDto.getSearchKeyword();
        String category = reqBookInfoDto.getSearchCategory();
        Integer totalCnt = null;

        if (!keyword.isBlank()) {
            keyword = "%" + keyword + "%";
            if (category.equals("title")) {
                totalCnt = bookInfoRepository.countByTitleLike(keyword);
            } else if (category.equals("author")) {
                totalCnt = bookInfoRepository.countByAuthorLike(keyword);
            } else if (category.equals("publisher")) {
                totalCnt = bookInfoRepository.countByPublisherLike(keyword);
            } else {
                totalCnt = bookInfoRepository.countByTitleLikeOrAuthorLikeOrPublisherLike(keyword, keyword, keyword);
            }
        } else {
            totalCnt = (int) bookInfoRepository.count();
        }

        return ResBookListDto.builder()
                .totalCnt(totalCnt)
                .currentPage(reqBookInfoDto.getPage())
                .totalPage((int) Math.ceil((double) totalCnt / reqBookInfoDto.getLimit()))
                .resBookInfoSmallDtos(listByRequest)
                .build();
    }
}
