package com.ssafy.bjbj.api.member.repository;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;

import java.util.List;

public interface MemberRepositoryCustom {

    ResLoginMemberDto findResLoginMemberDtoByEmail(String email);

    List<ActivityCountDto> findAllActivityCountDtoBySeq(Long seq);
    
}
