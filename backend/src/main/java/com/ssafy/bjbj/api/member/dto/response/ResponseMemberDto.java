package com.ssafy.bjbj.api.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = "id", "email", "name", "nickname", "phoneNumber")
@NoArgsConstructor
@Getter
@Builder
public class ResponseMemberDto {
}
