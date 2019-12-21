package com.web.book.dto;

/**
 * @anthor sily
 * @date 2019/12/6 - 20:22
 */
public class Result<T> {
    private boolean success;

    private T data;

    private String error;

    public Result() {

    }
    // 成功时的构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    //错误时的构造器
    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
