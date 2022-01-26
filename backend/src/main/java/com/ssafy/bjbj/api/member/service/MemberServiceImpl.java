package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public Member findMemberById(Long id) {
        return memberRepository.findMemberById(id);
    }

    @Override
    @Transactional
    public boolean saveMember(RequestMemberDto memberDto) {
        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());
        log.debug("패스워드 암호화 " + encryptedPassword);

        Member savedMember = memberRepository.save(Member.builder()
                .email(memberDto.getEmail())
                .password(encryptedPassword)
                .name(memberDto.getName())
                .nickname(memberDto.getNickname())
                .phoneNumber(memberDto.getPhoneNumber())
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        return savedMember.getId() != null;
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
    public RequestMemberDto findMemberDtoByEmail(String email) {
        return memberRepository.findMemberDtoByEmail(email);
    }

    @Transactional
    @Override
    public boolean subscribeMember(Long fromMemberId, Long toMemberId) {

        Member fromMember = memberRepository.findMemberById(fromMemberId);
        List<Subscribe> toMembers = fromMember.getToMembers();
        System.out.println("toMembers = " + toMembers);

        if (toMembers != null) {
            for (Subscribe subscribe : toMembers) {
                if (subscribe.getToMember().getId().equals(toMemberId)) {
                    return false;
                }
            }
        }

        Member toMember = memberRepository.findMemberById(toMemberId);
        Subscribe subscribe = Subscribe.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
//        fromMember.getToMembers().add(subscribe);
        subscribeRepository.save(subscribe);

        return true;
    }

    @Transactional
    @Override
    public boolean unsubscribeMember(Long fromMemberId, Long toMemberId) {

        Member fromMember = memberRepository.findMemberById(fromMemberId);
        List<Subscribe> toMembers = fromMember.getToMembers();

        for (Subscribe subscribe : toMembers) {
            if (subscribe.getToMember().getId().equals(toMemberId)) {
                subscribeRepository.delete(subscribe);
                return true;
            }
        }

        return false;
    }
}