package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;

import java.util.List;

public interface MemberRepositoryCustom {

    ResponseMemberDto findResponseMemberDtoByEmail(String email);

    List<ActivityCountDto> findAllActivityCountDtoBySeq(Long seq);
    
}
