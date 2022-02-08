package com.ssafy.bjbj.api.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString(of = {"seq", "email", "password", "name", "nickname", "phoneNumber", "point", "exp", "level"})
@Getter
public class ResLoginMemberDto {

    private Long seq;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    private Integer point;

    private Integer exp;

    private Integer level;

    @QueryProjection
    public ResLoginMemberDto(Long seq, String email, String password, String name, String nickname, String phoneNumber, Integer point, Integer exp) {
        this.seq = seq;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.exp = exp;
        this.level = exp / 100 + 1;
    }

}
