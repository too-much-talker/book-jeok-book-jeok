package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;

public interface MemberService {

    boolean hasEmail(String email);

    boolean hasNickname(String nickname);

    boolean saveMember(RequestMemberDto memberDto);

    Member findMemberByEmail(String email);

    RequestMemberDto findMemberDtoByEmail(String email);

}
