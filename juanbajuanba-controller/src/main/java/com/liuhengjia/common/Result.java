package com.liuhengjia.common;

import com.liuhengjia.constant.CodeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Result {
    @Builder.Default
    private Integer code = CodeConstant.SUCCESS;
    private Object data;
    private String message;
}
