package com.ssafy.bjbj.api.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@ToString(of = {"email", "password", "name", "nickname"})
@NoArgsConstructor
@Getter
@Builder
public class MemberDto {

    /**
     * 정규 표현식 -> 이메일 형식에 맞게.
     */
    @Email(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:[_a-z0-9-]+\\.)+[_a-z0-9-]+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    /**
     * 정규 표현식 -> 영어 대문자, 영어 소문자, 숫자, 특수문자 중 두 종류 이상 사용해서 8글자 이상 12자 이하
     * 패스워드에 이메일, 이름, 닉네임이 포함되면 안된다.
     */
    @Length(min = 8, max = 12, message = "패스워드는 8자 이상 12자 이하로 입력해주세요.")
    private String password;

    /**
     * 정규표현식 -> 영어만 또는 한글만 사용해서 2글자 이상 20자 이하
     */
    @Length(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    /**
     * 정규 표현식 -> 한글만 2글자 이상 8글자 이하 또는 영어만 3글자 이상 12글자 이하
     */
    @Pattern(regexp = "^[가-힣]{2,8}|[a-z]{3,12}$", message = "닉네임 형식에 맞지 않습니다.")
    private String nickname;

    @QueryProjection
    public MemberDto(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }
}
