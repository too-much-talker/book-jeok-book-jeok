package com.ssafy.bjbj.api.booklog.service;

import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.booklog.exception.DuplicateLikeException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundBooklogException;
import com.ssafy.bjbj.api.booklog.exception.NotFoundLikeException;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.booklog.repository.LikeRepository;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final BooklogRepository booklogRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void like(Long booklogSeq, Long memberSeq) {
        Like findLike = likeRepository.findByBooklogSeqAndMemberSeq(booklogSeq, memberSeq);
        if (findLike == null) {
            Booklog booklog = booklogRepository.findBySeq(booklogSeq);
            if (booklog == null) {
                throw new NotFoundBooklogException("올바르지 않은 요청입니다.");
            }

            Member member = memberRepository.findBySeq(memberSeq);

            likeRepository.save(Like.builder()
                    .booklog(booklog)
                    .member(member)
                    .build());
        } else {
            throw new DuplicateLikeException("요청을 수행할 수 없습니다.");
        }
    }

    @Transactional
    @Override
    public void unLike(Long booklogSeq, Long memberSeq) {
        Like findLike = likeRepository.findByBooklogSeqAndMemberSeq(booklogSeq, memberSeq);
        if (findLike == null) {
            throw new NotFoundLikeException("올바르지 않은 요청입니다.");
        } else if (findLike.getMember().getSeq().equals(memberSeq)) {
            likeRepository.delete(findLike);
        } else {
            throw new NotEqualMemberException("올바르지 않은 요청입니다.");
        }
    }

    @Override
    public boolean isLike(Long booklogSeq, Long memberSeq) {
        Like findLike = likeRepository.findByBooklogSeqAndMemberSeq(booklogSeq, memberSeq);
        return findLike != null;
    }

}
