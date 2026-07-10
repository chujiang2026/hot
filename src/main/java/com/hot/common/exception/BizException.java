package com.hot.common.exception;

import com.hot.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常：用于在 service 层中断流程并向前端返回明确的错误码与提示。
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ResultCode resultCode;
    private final String extra;

    public BizException(ResultCode resultCode) {
        this(resultCode, null);
    }

    public BizException(ResultCode resultCode, String extra) {
        super(extra == null ? resultCode.getMsg() : resultCode.getMsg() + "-" + extra);
        this.resultCode = resultCode;
        this.extra = extra;
    }
}
