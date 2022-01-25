package com.ssafy.bjbj.api.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"id", "email", "password", "name", "nickname", "phoneNumber"})
@NoArgsConstructor
@Getter
@Builder
public class ResponseMemberDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    @QueryProjection
    public ResponseMemberDto(Long id, String email, String password, String name, String nickname, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

}
