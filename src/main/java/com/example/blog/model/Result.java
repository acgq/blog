package com.example.blog.model;

public abstract class Result<T> {
    String status;
    String msg;
    T data;


    public Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }


    public Object getData() {
        return data;
    }
}