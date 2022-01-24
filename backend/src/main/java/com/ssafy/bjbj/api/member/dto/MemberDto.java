package com.ssafy.bjbj.api.member.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@ToString(of = {"email", "password", "name", "nickname"})
@NoArgsConstructor
@Getter
@AllArgsConstructor
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
     * 정규 표현식 -> ?
     */
    @Length(min = 3, max = 8, message = "닉네임은 3자 이상 8자 이하로 입력해주세요.")
    private String nickname;

}
