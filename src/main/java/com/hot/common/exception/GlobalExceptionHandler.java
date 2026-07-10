package com.hot.common.exception;

import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        log.warn("biz exception: {}", e.getMessage());
        return Result.fail(e.getResultCode(), e.getExtra());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("unhandled exception", e);
        return Result.fail(ResultCode.SERVER_ERR, e.getMessage());
    }
}
