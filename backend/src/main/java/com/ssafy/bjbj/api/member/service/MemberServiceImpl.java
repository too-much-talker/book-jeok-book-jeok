package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.entity.Subscribe;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import com.ssafy.bjbj.api.member.repository.SubscribeRepository;
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
                .exp(0)
                .point(100)
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

}
