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
}
