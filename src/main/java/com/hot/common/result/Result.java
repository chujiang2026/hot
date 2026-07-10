package com.hot.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> of(ResultCode rc) {
        return new Result<>(rc.getCode(), rc.getMsg(), null);
    }

    public static <T> Result<T> of(ResultCode rc, T data) {
        return new Result<>(rc.getCode(), rc.getMsg(), data);
    }

    public static <T> Result<T> fail(ResultCode rc, String extra) {
        String msg = extra == null || extra.isEmpty() ? rc.getMsg() : rc.getMsg() + "-" + extra;
        return new Result<>(rc.getCode(), msg, null);
    }
}