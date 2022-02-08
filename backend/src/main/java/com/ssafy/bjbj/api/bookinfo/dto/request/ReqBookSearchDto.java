package com.ssafy.bjbj.api.bookinfo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = {"page", "limit", "searchCategory", "searchKeyword", "orderCategory"})
@NoArgsConstructor
@Getter
public class ReqBookSearchDto {

    private Integer page;

    private Integer limit;

    private String searchCategory;

    private String searchKeyword;

    private String orderCategory;

    @Builder
    public ReqBookSearchDto(int page, int limit, String searchCategory, String searchKeyword, String orderCategory) {
        this.page = page;
        this.limit = limit;
        this.searchCategory = searchCategory;
        this.searchKeyword = searchKeyword;
        this.orderCategory = orderCategory;
    }

}
