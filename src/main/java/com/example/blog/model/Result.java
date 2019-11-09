package com.example.blog.model;

public class Result {
    private String status;
    private String msg;
    private boolean isLogin;
    private Object data;

    public static Result fail(String msg) {
        return new Result("fail", msg, false);
    }

    public static Result success(String msg, Object object) {
        return new Result("success", msg, true, object);
    }

    private Result(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Result(String status, String msg, boolean isLogin, Object object) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = object;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
