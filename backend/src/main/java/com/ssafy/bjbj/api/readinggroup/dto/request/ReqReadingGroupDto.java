package com.ssafy.bjbj.api.readinggroup.dto.request;

import com.ssafy.bjbj.api.readinggroup.enums.ReadingGroupType;
import com.ssafy.bjbj.common.enums.Day;
import com.ssafy.bjbj.common.validator.EnumListValidator;
import com.ssafy.bjbj.common.validator.EnumValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.util.List;

@ToString(of = { "title", "content", "limitLevel", "maxMember", "deadline", "startDate", "endDate", "readingGroupType", "days" })
@NoArgsConstructor
@Getter
public class ReqReadingGroupDto {

    private String title;

    private String content;

    @Range(min = 1, max = 50, message = "제한 레벨은 1 이상 50 이하로 설정할 수 있습니다.")
    private Integer limitLevel;

    @Range(min = 3, max = 10, message = "최대 인원은 3 이상 10 이하로 설정할 수 있습니다.")
    private Integer maxMember;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String deadline;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String startDate;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String endDate;

    @EnumValidator(enumClass = ReadingGroupType.class, ignoreCase = true, message = "독서 모임 분위기 타입을 올바르게 설정해주세요.")
    private String readingGroupType;

    @EnumListValidator(enumClass = Day.class, ignoreCase = true, message = "요일을 올바르게 설정해주세요.")
    private List<String> days;

    @Builder
    public ReqReadingGroupDto(String title, String content, Integer limitLevel, Integer maxMember, String deadline, String startDate, String endDate, String readingGroupType, List<String> days) {
        this.title = title;
        this.content = content;
        this.limitLevel = limitLevel;
        this.maxMember = maxMember;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.readingGroupType = readingGroupType;
        this.days = days;
    }

}
