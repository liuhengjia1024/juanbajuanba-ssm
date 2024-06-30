package com.liuhengjia.interceptor;

import com.liuhengjia.constant.CodeConstant;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class ProjectExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ProjectExceptionAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException bs) {
        String message = bs.getMessage();
        return Result.builder().code(CodeConstant.ERROR)
                .message(message == null ? MessageConstant.BUSINESS_ERROR : message).build();
    }

    @ExceptionHandler(SystemException.class)
    public Result systemExceptionHandler(BusinessException bs) {
        // mq 给管理员
        log.error("MQ 给管理员：{}", bs.getMessage());
        return Result.builder().code(CodeConstant.ERROR).message(bs.getMessage()).build();
    }
}
