package com.hot.common.result;

public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(300, "操作失败"),
    EMPTY(400, "空数据"),
    NO_LOGIN(401, "未登录"),
    LOGIN_FAIL(402, "账号或密码不正确"),
    LOGIN_LOCK(403, "用户被锁定"),
    SERVER_ERR(500, "服务器错误"),
    FEISHU_FAIL(600, "飞书授权失败"),
    EXIS_PROJECT(700, "已关联合作不可删除");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
