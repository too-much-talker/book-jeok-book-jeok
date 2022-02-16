package com.ssafy.bjbj.api.challenge.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ReqChallengeAuthDto {

    @NotEmpty(message = "제목을 작성해주세요.")
    private String title;

    private String content;
}
