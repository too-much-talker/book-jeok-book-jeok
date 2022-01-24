package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.MemberDto;

public interface MemberRepositoryCustom {

    MemberDto findMemberDtoByEmail(String email);
}
