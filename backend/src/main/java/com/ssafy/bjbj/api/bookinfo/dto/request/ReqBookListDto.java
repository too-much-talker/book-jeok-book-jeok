package com.ssafy.bjbj.api.bookinfo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class ReqBookListDto {

    private Integer page;

    private Integer limit;

    private String searchCategory;

    private String searchKeyword;

    private String orderCategory;

    @Builder
    public ReqBookListDto(int page, int limit, String searchCategory, String searchKeyword, String orderCategory) {
        this.page = page;
        this.limit = limit;
        this.searchCategory = searchCategory;
        this.searchKeyword = searchKeyword;
        this.orderCategory = orderCategory;
    }
}
