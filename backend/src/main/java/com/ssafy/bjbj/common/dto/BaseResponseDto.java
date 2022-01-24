package com.ssafy.bjbj.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseResponseDto {
    
    private Integer status;

    private Object data;

}
