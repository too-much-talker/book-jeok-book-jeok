package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Subscribe;

import java.util.List;

public interface MemberService {

    boolean hasEmail(String email);

    boolean hasNickname(String nickname);

    boolean saveMember(RequestMemberDto memberDto);

    Member findMemberByEmail(String email);

    ResponseMemberDto findResponseMemberDtoByEmail(String email);

    Integer getPointById(Long id);

    Integer getExpById(Long id);

    List<ActivityCountDto> getAllActivityCounts(Long id);

    Member findMemberById(Long id);

    boolean subscribeMember(Long fromMemberId, Long toMemberId);

    boolean unsubscribeMember(Long fromMemberId, Long toMemberId);

}
