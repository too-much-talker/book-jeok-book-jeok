package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.bookinfo.repository.BookReviewRepository;
import com.ssafy.bjbj.api.booklog.entity.Like;
import com.ssafy.bjbj.api.booklog.repository.BooklogRepository;
import com.ssafy.bjbj.api.booklog.repository.LikeRepository;
import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.request.ReqLoginMemberDto;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.entity.Subscribe;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.repository.SubscribeRepository;
import com.ssafy.bjbj.api.readinggroup.entity.ReadingGroupMember;
import com.ssafy.bjbj.api.readinggroup.exception.NotAcceptResignException;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardCommentRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupBoardRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupMemberRepository;
import com.ssafy.bjbj.api.readinggroup.repository.ReadingGroupRepository;
import com.ssafy.bjbj.common.exception.NotEqualMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final SubscribeRepository subscribeRepository;

    private final BooklogRepository booklogRepository;

    private final BookReviewRepository bookReviewRepository;

    private final ReadingGroupRepository readingGroupRepository;

    private final ReadingGroupBoardRepository readingGroupBoardRepository;

    private final ReadingGroupBoardCommentRepository readingGroupBoardCommentRepository;

    private final LikeRepository likeRepository;

    private final ReadingGroupMemberRepository readingGroupMemberRepository;

    @Override
    public boolean hasEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean hasNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public Member findMemberBySeq(Long seq) {
        return memberRepository.findMemberBySeq(seq);
    }

    @Override
    @Transactional
    public Member register(ReqMemberDto reqMemberDto) {
        String encryptedPassword = passwordEncoder.encode(reqMemberDto.getPassword());
        log.debug("패스워드 암호화 " + encryptedPassword);

        return memberRepository.save(Member.builder()
                .email(reqMemberDto.getEmail())
                .password(encryptedPassword)
                .name(reqMemberDto.getName())
                .nickname(reqMemberDto.getNickname())
                .phoneNumber(reqMemberDto.getPhoneNumber())
                .role(Role.MEMBER)
                .build());
    }

    /**
     * @param email
     * @return 회원엔티티반환
     */
    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public ResLoginMemberDto findResLoginMemberDtoByEmail(String email) {
        return memberRepository.findResLoginMemberDtoByEmail(email);
    }

    @Override
    public Member findMemberByNickname(String nickname) {
        return memberRepository.findMemberByNickname(nickname);
    }

    public Integer getPointBySeq(Long seq) {
        return memberRepository.findPointBySeq(seq);
    }

    @Override
    public Integer getExpBySeq(Long seq) {
        return memberRepository.findExpBySeq(seq);
    }

    @Override
    public List<ActivityCountDto> getAllActivityCounts(Long seq) {
        return memberRepository.findAllActivityCountDtoBySeq(seq);
    }

    @Transactional
    @Override
    public boolean updateMember(ReqMemberDto reqMemberDto, Long seq) {

        String encryptedPassword = passwordEncoder.encode(reqMemberDto.getPassword());
        Member member = memberRepository.findMemberBySeq(seq);
        member.changeMember(encryptedPassword, reqMemberDto.getName(), reqMemberDto.getNickname(), reqMemberDto.getPhoneNumber());
    
        return true;
    }

    @Transactional
    @Override
    public boolean subscribeMember(Long fromMemberSeq, Long toMemberSeq) {

        Member fromMember = memberRepository.findMemberBySeq(fromMemberSeq);
        List<Subscribe> toMembers = fromMember.getToMembers();
        System.out.println("toMembers = " + toMembers);

        if (toMembers != null) {
            for (Subscribe subscribe : toMembers) {
                if (subscribe.getToMember().getSeq().equals(toMemberSeq)) {
                    return false;
                }
            }
        }

        Member toMember = memberRepository.findMemberBySeq(toMemberSeq);
        Subscribe subscribe = Subscribe.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
        subscribeRepository.save(subscribe);

        return true;
    }

    @Transactional
    @Override
    public boolean unsubscribeMember(Long fromMemberSeq, Long toMemberSeq) {

        Member fromMember = memberRepository.findMemberBySeq(fromMemberSeq);
        List<Subscribe> toMembers = fromMember.getToMembers();

        for (Subscribe subscribe : toMembers) {
            if (subscribe.getToMember().getSeq().equals(toMemberSeq)) {
                subscribeRepository.delete(subscribe);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    @Override
    public void resignMember(ReqLoginMemberDto reqLoginMemberDto, Long memberSeq) {
        Member member = memberRepository.findMemberBySeq(memberSeq);

        if (member == null || member.isDeleted()) {
            throw new NotEqualMemberException("잘못된 요청입니다.");
        } else if (!member.getEmail().equals(reqLoginMemberDto.getEmail()) || !passwordEncoder.matches(reqLoginMemberDto.getPassword(), member.getPassword())) {
            throw new NotEqualMemberException("잘못된 요청입니다.");
        }

        boolean isReadingGroup = readingGroupRepository.existReadingGroupByMemberSeq(memberSeq);
        if (!isReadingGroup) {
            throw new NotAcceptResignException("개설한 독서모임을 삭제해 주세요.");
        }

        booklogRepository.deleteBooklogByMemberSeq(memberSeq);
        bookReviewRepository.deleteAllByMemberSeq(memberSeq);
        readingGroupBoardRepository.deleteAllByMemberSeq(memberSeq);
        readingGroupBoardCommentRepository.deleteAllByMemberSeq(memberSeq);

        subscribeRepository.deleteAllByFromMemberSeq(memberSeq);
        likeRepository.deleteAllByMemberSeq(memberSeq);
        readingGroupMemberRepository.deleteAllByMemberSeq(memberSeq);

        member.delete();
    }
}
