package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.MemberDto;
import com.ssafy.bjbj.api.member.entity.Member;

public interface MemberService {

    boolean hasEmail(String email);

    boolean hasNickname(String nickname);

    Member findMemberByEmail(String email);

}
