package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean hasEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean hasNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
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
    public ResponseMemberDto findResponseMemberDtoByEmail(String email) {
        return memberRepository.findResponseMemberDtoByEmail(email);
    }

    @Override
    public Member findMemberByNickname(String nickname) {
        return memberRepository.findMemberByNickname(nickname);
    }

    @Transactional
    @Override
    public boolean updateMember(RequestMemberDto memberDto, Long id) {

        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());
        Member member = memberRepository.findMemberById(id);
        member.changeMember(encryptedPassword, memberDto.getName(), memberDto.getNickname(), memberDto.getPhoneNumber());

        return true;
    }


}
