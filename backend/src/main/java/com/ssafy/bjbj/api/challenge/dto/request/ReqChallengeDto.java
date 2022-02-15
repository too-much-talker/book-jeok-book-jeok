package com.ssafy.bjbj.api.challenge.dto.request;

import com.ssafy.bjbj.api.challenge.entity.Challenge;
import com.ssafy.bjbj.api.member.entity.Member;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
public class ReqChallengeDto {

    private String title;

    private String content;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String deadline;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String startDate;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String endDate;

    @Range(min = 3, max = 10, message = "최대 인원은 3 이상 10 이하로 설정할 수 있습니다.")
    private Integer maxMember;

    @Range(min = 1, max = 1000, message = "리워드는 1 이상 1,000 이하로 설정할 수 있습니다.")
    private Integer reward;

    public Challenge toEntity(Member member) {
        String time = "T00:00:00";
        return Challenge.builder()
                .title(this.title)
                .content(this.content)
                .deadline(LocalDateTime.parse(this.deadline + time))
                .startDate(LocalDateTime.parse(this.startDate + time))
                .endDate(LocalDateTime.parse(this.endDate + time))
                .maxMember(this.maxMember)
                .reward(this.reward)
                .member(member)
                .build();
    }

}
