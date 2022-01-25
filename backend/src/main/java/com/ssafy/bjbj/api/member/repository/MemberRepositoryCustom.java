package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;

public interface MemberRepositoryCustom {

    RequestMemberDto findMemberDtoByEmail(String email);
    
}
