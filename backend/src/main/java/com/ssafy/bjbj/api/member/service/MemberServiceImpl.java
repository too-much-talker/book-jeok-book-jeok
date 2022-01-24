package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean hasEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean hasNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

}
