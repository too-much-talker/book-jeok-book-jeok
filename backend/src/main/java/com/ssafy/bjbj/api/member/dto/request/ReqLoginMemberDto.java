package com.ssafy.bjbj.api.member.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@ToString(of = {"email", "password"})
@Getter
public class ReqLoginMemberDto {

    /**
     * 정규 표현식 -> 이메일 형식에 맞게.
     */
    @Email(regexp = "^[0-9a-z]+([.-]?[0-9a-z]+)*@[0-9a-z]+([.-]+[0-9a-z]+)*(\\.[0-9a-z]{2,3})+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    /**
     * 정규 표현식 -> 영어 대문자, 영어 소문자, 숫자, 특수문자 중 두 종류 이상 사용해서 8글자 이상 12자 이하
     */
    @Pattern(regexp = "([0-9]+[A-Z]+|[A-Z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[a-z]+|[a-z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[!@#$%]+|[!@#$%]+[0-9])+[0-9a-zA-Z!@#$%]*|([A-Z]+[a-z]+|[a-z]+[A-Z])+[0-9a-zA-Z!@#$%]*|([A-Z]+[!@#$%]+|[!@#$%]+[A-Z])+[0-9a-zA-Z!@#$%]*|([a-z]+[!@#$%]+|[!@#$%]+[a-z])+[0-9a-zA-Z!@#$%]*", message = "패스워드 규칙에 맞지 않습니다.")
    @Length(min = 8, max = 12, message = "패스워드는 8자 이상 12자 이하로 입력해주세요.")
    private String password;

}
