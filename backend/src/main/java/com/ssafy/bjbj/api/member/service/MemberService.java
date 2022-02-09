package com.ssafy.bjbj.api.member.service;

import com.ssafy.bjbj.api.member.dto.ActivityCountDto;
import com.ssafy.bjbj.api.member.dto.request.ReqMemberDto;
import com.ssafy.bjbj.api.member.dto.response.ResLoginMemberDto;
import com.ssafy.bjbj.api.member.entity.Member;

import java.util.List;

public interface MemberService {

    boolean hasEmail(String email);

    boolean hasNickname(String nickname);

    Member register(ReqMemberDto reqMemberDto);

    Member findMemberByEmail(String email);

    ResLoginMemberDto findResLoginMemberDtoByEmail(String email);

    Member findMemberByNickname(String nickname);

    boolean updateMember(ReqMemberDto reqMemberDto, Long seq);

    Integer getPointBySeq(Long seq);

    Integer getExpBySeq(Long seq);

    List<ActivityCountDto> getAllActivityCounts(Long seq);

    Member findMemberBySeq(Long seq);

    boolean subscribeMember(Long fromMemberSeq, Long toMemberSeq);

    boolean unsubscribeMember(Long fromMemberSeq, Long toMemberSeq);

    boolean hasPhoneNumber(String phoneNumber);

}
