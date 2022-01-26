package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.request.RequestMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResponseMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;

import java.util.List;

public interface MemberService {

    boolean hasEmail(String email);

    boolean hasNickname(String nickname);

    boolean saveMember(RequestMemberDto memberDto);

    Member findMemberByEmail(String email);

    ResponseMemberDto findResponseMemberDtoByEmail(String email);

    Member findMemberByNickname(String nickname);

    boolean updateMember(RequestMemberDto memberDto, Long seq);

    Integer getPointBySeq(Long seq);

    Integer getExpBySeq(Long seq);

    List<ActivityCountDto> getAllActivityCounts(Long seq);

    Member findMemberBySeq(Long seq);

    boolean subscribeMember(Long fromMemberSeq, Long toMemberSeq);

    boolean unsubscribeMember(Long fromMemberSeq, Long toMemberSeq);

}
