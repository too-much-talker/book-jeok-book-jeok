package com.ssafy.bjbj.api.member.dto.request;

import lombok.*;

@ToString(of = {"email", "password", "name", "nickname", "phoneNumber"})
@NoArgsConstructor
@Getter
@Builder
public class RequestMemberDto {
}
