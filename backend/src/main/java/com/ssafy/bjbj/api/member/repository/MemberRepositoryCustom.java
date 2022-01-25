package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;

public interface MemberRepositoryCustom {

    ResponseMemberDto findResponseMemberDtoByEmail(String email);
    
}
