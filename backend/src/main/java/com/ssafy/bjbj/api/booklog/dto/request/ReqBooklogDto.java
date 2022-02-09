package com.ssafy.bjbj.api.booklog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ToString(of = { "memberSeq", "bookInfoSeq", "title", "content", "summary", "starRating", "readDate", "isOpen"})
@NoArgsConstructor
@Getter
public class ReqBooklogDto {

    @NotNull(message = "회원 식별키가 없습니다.")
    private Long memberSeq;

    @NotNull(message = "책 정보 식별키가 없습니다.")
    private Long bookInfoSeq;

    @NotBlank(message = "제목이 작성되지 않았습니다.")
    private String title;

    private String content;

    private String summary;

    @Range(min = 1, max = 5, message = "별점은 1, 2, 3, 4, 5점만 가능합니다.")
    private Integer starRating;

    @Pattern(regexp = "^(20[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))?", message = "날짜 형식에 맞지 않습니다.")
    private String readDate;

    @NotNull(message = "공개여부가 설정되지 않았습니다.")
    private Boolean isOpen;

    @Builder
    public ReqBooklogDto(Long memberSeq, Long bookInfoSeq, String title, String content, String summary, Integer starRating, String readDate, Boolean isOpen) {
        this.memberSeq = memberSeq;
        this.bookInfoSeq = bookInfoSeq;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.starRating = starRating;
        this.readDate = readDate;
        this.isOpen = isOpen;
    }

}
