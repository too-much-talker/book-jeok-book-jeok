package com.ssafy.bjbj.api.readinggroup.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString(of = {"memberSeq","readingGroupSeq", "title", "content", "nickname", "createDate"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResReadingGroupBoardDto {

    private Long memberSeq;

    private Long readingGroupSeq;

    private String title;

    private String content;

    private String nickname;

    private LocalDateTime createDate;

}
