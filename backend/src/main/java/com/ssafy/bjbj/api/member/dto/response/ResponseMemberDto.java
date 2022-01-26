package com.ssafy.bjbj.api.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString(of = {"seq", "email", "password", "name", "nickname", "phoneNumber"})
@Getter
public class ResponseMemberDto {

    private Long seq;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    @QueryProjection
    public ResponseMemberDto(Long seq, String email, String password, String name, String nickname, String phoneNumber) {
        this.seq = seq;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

}
