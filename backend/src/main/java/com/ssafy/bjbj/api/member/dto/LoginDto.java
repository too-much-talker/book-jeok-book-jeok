package com.ssafy.bjbj.api.member.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@ToString(of = {"email", "password"})
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class LoginDto {

    /**
     * 정규 표현식 -> 이메일 형식에 맞게.
     */
    @Email(regexp = "^[0-9a-zA-Z]+([.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+([.-]+[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]{2,3})+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    /**
     * 정규 표현식 -> 영어 대문자, 영어 소문자, 숫자, 특수문자 중 두 종류 이상 사용해서 8글자 이상 12자 이하
     * 패스워드에 이메일, 이름, 닉네임이 포함되면 안된다.
     */
    @Length(min = 8, max = 12, message = "패스워드는 8자 이상 12자 이하로 입력해주세요.")
    private String password;

}
