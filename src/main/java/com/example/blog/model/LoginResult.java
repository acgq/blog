package com.example.blog.model;

public class LoginResult extends Result<User> {
    private boolean isLogin;

    public static LoginResult success(String msg) {
        return new LoginResult("ok", msg, null);
    }

    public static LoginResult success(String msg, User user) {
        return new LoginResult("ok", msg, user);
    }

    public static LoginResult success(String msg, boolean isLogin) {
        LoginResult result = new LoginResult("ok", msg, null);
        result.isLogin = isLogin;
        return result;
    }

    public static LoginResult fail(String msg) {
        return new LoginResult("fail", msg, null);
    }

    private LoginResult(String status, String msg, User user) {
        super(status, msg, user);
    }

    public boolean getIsLogin() {
        return isLogin;
    }

}
